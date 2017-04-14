package com.thinkeract.tka.ui.preview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.thinkeract.tka.R;
import com.thinkeract.tka.User;
import com.thinkeract.tka.common.utils.PermissionUtils;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.widget.BottomLinearPicker;
import com.thinkeract.tka.widget.CommonDialog;
import com.zitech.framework.crop.CropActivity;
import com.zitech.framework.utils.ImageUtils;
import com.zitech.framework.utils.ToastMaster;
import com.zitech.framework.utils.Utils;

import java.io.File;

/**
 * 选取照片的基础类
 *
 * @author daiqian
 * @since 1.0
 */
public abstract class PhotoPickingActivity extends AppBarActivity {
    private static final String TOOK_PHOTO_NAME = "_temp.jpg";
    private static final String COMPRESSED_PHOTO_NAME = "_saved.jpg";
    //
    protected static final int TAKE_PHOTO = 9997;
    protected static final int SELECT_FROM_ALBUMS = 9998;
    protected static final int CROP_PIC = 9999;
    //
    public static final int EFFECT_TYPE_CUT = 1;
    public static final int EFFECT_TYPE_NONE = 0;
    private int effecType;
    private float ratio = 1f;

    protected static final int READ_WRITE_FOR_AVATAR = 103;//读写权限
    protected static final int CAMERA_AVATAR = 105;//相机权限
    private BottomLinearPicker bottomLinearPicker;

    public interface PhotoTakeListener {
        /**
         * 拍照或者选择图库成功获取图片
         *
         * @param picturePath
         */
        public void onPhotoTake(String picturePath);

        /**
         * 成功获取剪切过的图片
         *
         * @param picturePath
         */
        public void onPhotoCut(String picturePath, String cutPicturePath);

        public void onFailure();
    }

    private PhotoTakeListener listener;

    public void requestTakePhoto(String tilte, int effecType, float ratio, PhotoTakeListener listener) {
        this.ratio = ratio;
        requestTakePhoto(tilte, effecType, listener);
    }

    public void requestTakePhoto(String title, int effecType, PhotoTakeListener listener) {
        this.effecType = effecType;
        this.listener = listener;
        initDialogMenu(title);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                if (requestCode == TAKE_PHOTO) {
                    File photoTookedPath = getPhotoTookPath();
                    if (photoTookedPath != null && photoTookedPath.exists()) {
                        if (effecType == EFFECT_TYPE_CUT) {
                            cutPhoto(Uri.fromFile(photoTookedPath), ImageUtils.MAX_LIMIT);
                        } else {
                            String picturePath = ImageUtils.compressNoLargePhoto(this, photoTookedPath, getCompressedPhotoPath(),
                                    true, ImageUtils.MAX_LIMIT);
                            if (listener != null) {
                                listener.onPhotoTake(picturePath);
                                listener = null;
                            }
                        }
                    } else {
                        if (listener != null) {
                            listener.onFailure();
                            listener = null;
                        }

                    }
                } else if (requestCode == SELECT_FROM_ALBUMS) {
                    if (data == null) {
                        if (listener != null) {
                            listener.onFailure();
                            listener = null;
                        }
                        return;
                    }
                    Uri uri = data.getData();
                    if (EFFECT_TYPE_CUT == effecType) {
                        cutPhoto(uri, ImageUtils.MAX_LIMIT);
                    } else {
                        String photoTookPath = Utils.getPathFromUri(this, uri);
                        String picturePath = ImageUtils.compressNoLargePhoto(this, new File(photoTookPath),
                                getCompressedPhotoPath(), true, ImageUtils.MAX_LIMIT);
                        if (listener != null) {
                            listener.onPhotoTake(picturePath);
                            listener = null;
                        }
                    }
                } else if (requestCode == CROP_PIC) {
                    if (data == null || data.getExtras() == null) {
                        if (listener != null) {
                            listener.onFailure();
                            listener = null;
                        }
                        return;
                    }
                    String picturePath = ImageUtils.compressNoLargePhoto(this, new File(data.getExtras().getString("src")),
                            getCompressedPhotoPath(), true, ImageUtils.MAX_LIMIT);
                    if (listener != null) {
                        listener.onPhotoCut(picturePath, data.getExtras().getString("data"));
                        listener = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onFailure();
                    listener = null;
                }
            }

        } else if (requestCode == TAKE_PHOTO || requestCode == SELECT_FROM_ALBUMS || requestCode == CROP_PIC) {
            if (listener != null) {
                listener.onFailure();
                listener = null;
            }
        }

    }


    public void cutPhoto(Uri uri) {
        Intent intent = new Intent(this, CropActivity.class);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("ratio", ratio);
        startActivityForResult(intent, CROP_PIC);

    }

    public void onItemClick(int itemId) {
        switch (itemId) {
            case 2:
                if (PermissionUtils.isGrantExternalRW(this, READ_WRITE_FOR_AVATAR)) {
                    selectFromAlbum(listener);
                }
                break;
            case 1:
                if (PermissionUtils.isGrantCamera(this, CAMERA_AVATAR)) {
                    takePhoto(listener);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //业务逻辑
            if (requestCode == CAMERA_AVATAR) {
                takePhoto(listener);
            }else if (requestCode == READ_WRITE_FOR_AVATAR){
                selectFromAlbum(listener);
            }
        } else {
            //授权被拒绝，不再进行基于该权限的功能
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //用户已经完全拒绝，或手动关闭了权限
                    //开启此对话框缓解一下尴尬...
                    CommonDialog commonDialog = new CommonDialog(this, "不开读写权限将无法正常上传照片，请在设置中手动开启！");
                    commonDialog.show();
                } else if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    CommonDialog commonDialog = new CommonDialog(this, "不开相机权限将无法拍照，请在设置中手动开启！");
                    commonDialog.show();
                } else {
                    //用户一直拒绝并一直不勾选“不再提醒”
                    ToastMaster.shortToast("无法取得访问相机或相册权限，请在设置中开启");
                }
            }

        }
    }


    @SuppressWarnings("deprecation")
    private void initDialogMenu(String title) {
//        final ActionSheet sheet = (ActionSheet) ActionSheetHelper.createDialog(this, null);
//        String[] items = null;
//        items = getResources().getStringArray(R.array.detail_camer_pic);
//        sheet.addButton(items[0], ActionSheet.WHITE_STYLE_BTN);
//        sheet.addButton(items[1], ActionSheet.WHITE_STYLE_BTN);
//        sheet.setMainTitle(title);
//        sheet.setOnButtonClickListener(new ActionSheet.OnButtonClickListener() {
//
//            @Override
//            public void OnClick(View clickedView, int which) {
//                // TODO Auto-generated method stub
//                onItemClick(which);
//                sheet.dismiss();
//            }
//        });
//        sheet.addCancelButton(items[2]);
//        sheet.show();
        if(bottomLinearPicker == null) {
            bottomLinearPicker = new BottomLinearPicker(this, title);
            bottomLinearPicker.addText("拍照", R.color.blue_5080d8);
            bottomLinearPicker.addText("从手机相册选择", R.color.text_pink);
            bottomLinearPicker.setPickerListener(new BottomLinearPicker.ItemPickerListener() {
                @Override
                public void onPicked(int itemIndex, String itemStr) {
                    onItemClick(itemIndex);
                }
            });
        }
        bottomLinearPicker.show();
    }

    public void takePhoto(PhotoTakeListener photoTakeListener) {
        this.listener = photoTakeListener;
        if (Utils.checkSdCardAvailable()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File tookPath = getPhotoTookPath();
            if (tookPath.exists())
                tookPath.delete();
            Uri outputFileUri = Uri.fromFile(tookPath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, TAKE_PHOTO);
            }
        } else {
            ToastMaster.shortToast("请插入SD卡");
        }
    }

    public void selectFromAlbum(PhotoTakeListener photoTakeListener) {
        this.listener = photoTakeListener;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, SELECT_FROM_ALBUMS);
        }
    }

    private File getPhotoTookPath() {
        return Utils.getDiskCachePath(TOOK_PHOTO_NAME);
    }

    private File getCompressedPhotoPath() {
        String userId = String.valueOf(User.get().getId());
        userId = (userId + "_");
        return Utils.getDiskCachePath(userId + System.currentTimeMillis() + COMPRESSED_PHOTO_NAME);
    }

    public void cutPhoto(Uri uri, int limit) {
        Intent intent = new Intent(this, CropActivity.class);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("ratio", ratio);
        intent.putExtra("limit", limit);
        startActivityForResult(intent, CROP_PIC);

    }

}
