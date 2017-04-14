package com.thinkeract.tka.ui.mall.datasource;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.thinkeract.tka.common.utils.PagedProxy;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.GoodsItem;
import com.thinkeract.tka.data.api.request.GoodsFilterBody;
import com.thinkeract.tka.data.api.response.ListData;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by minHeng on 2017/4/13 11:40.
 * mail:minhengyan@gmail.com
 */

public class AllGoodsDataSource implements IAsyncDataSource<List<GoodsItem>> {
    private PagedProxy proxy = new PagedProxy(10);
    private boolean mIsOnlyOnePage;
    private String classifyId;
    private String orderType;

    public AllGoodsDataSource() {
        super();
    }

    public void setClassifyFilter(String classifyId){
        this.classifyId = classifyId;
    }

    public void setOrderFilter(String orderType){
        this.orderType = orderType;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<GoodsItem>> sender) throws Exception {
        return load(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<GoodsItem>> sender) throws Exception {
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

    private RequestHandle load(final ResponseSender<List<GoodsItem>> sender, final int page) throws Exception {
        GoodsFilterBody body=new GoodsFilterBody();
        body.setPage(page);
        body.setPageSize(proxy.getPageSize());
        body.setClassId(classifyId);
        body.setOrder(orderType);

        final Disposable subscription = ApiFactory.allGoods(body).subscribe(new Consumer<ApiResponse<ListData<GoodsItem>>>() {
            @Override
            public void accept(ApiResponse<ListData<GoodsItem>> listDataApiResponse) throws Exception {
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(listDataApiResponse.getData().getPageInfo().getCountX());
                }
                mIsOnlyOnePage = listDataApiResponse.getData().getPageInfo().getPageCount() == 1;
                mIsOnlyOnePage = true;
                List<GoodsItem> items = listDataApiResponse.getData().getItems();
                if (items == null || items.size() == 0) {
                    proxy.setReachEnd(true);
                }
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
