package com.thinkeract.tka.ui.home.datasource;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.thinkeract.tka.common.utils.PagedProxy;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.AddressItem;
import com.thinkeract.tka.data.api.request.ListBody;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by minHeng on 2016/12/7 11:40.
 * mail:minhengyan@gmail.com
 */

public class UserAddressDataSource implements IAsyncDataSource<List<AddressItem>> {
    private PagedProxy proxy = new PagedProxy(20);
    private boolean mIsOnlyOnePage;

    public UserAddressDataSource() {
        super();
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<AddressItem>> sender) throws Exception {
        return load(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<AddressItem>> sender) throws Exception {
        return load(sender, proxy.toNextPage());
    }

    @Override
    public boolean hasMore() {
        return proxy.hasNextPage();
    }

    @Override
    public boolean isOnlyOnePage() {
        return mIsOnlyOnePage;
    }

    private RequestHandle load(final ResponseSender<List<AddressItem>> sender, final int page) throws Exception {
        ListBody body=new ListBody();
        body.setPage(page);
        body.setPageSize(proxy.getPageSize());

        final Disposable subscription = ApiFactory.getUserAddressList(body).subscribe(new Consumer<ApiResponse<List<AddressItem>>>() {
            @Override
            public void accept(ApiResponse<List<AddressItem>> listDataApiResponse) throws Exception {
//                if (!proxy.isPageCountSet()) {
//                    proxy.setDataCount(listDataApiResponse.getData().getPageInfo().getCountX());
//                }
                proxy.setPageCount(1);
                mIsOnlyOnePage = true;
                List<AddressItem> items = listDataApiResponse.getData();
//                if (items == null || items.size() == 0) {
//                    proxy.setReachEnd(true);
//                }
                proxy.setReachEnd(false);
                sender.sendData(items);
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                sender.sendError(new Exception(throwable));
            }
        });

        return new RequestHandle() {
            @Override
            public void cancel() {
                subscription.dispose();
            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
    }
}
