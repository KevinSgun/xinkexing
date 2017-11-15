package com.thinkeract.tka.ui.mine.datasource;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.thinkeract.tka.common.utils.PagedProxy;
import com.thinkeract.tka.data.api.ApiFactory;
import com.thinkeract.tka.data.api.entity.OrderItem;
import com.thinkeract.tka.data.api.request.StatusListBody;
import com.thinkeract.tka.data.api.response.ListData;
import com.zitech.framework.data.network.response.ApiResponse;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by minHeng on 2016/12/17 15:58.
 * mail:minhengyan@gmail.com
 */

public class LogisticsDataSource implements IAsyncDataSource<List<OrderItem>> {
    private  String status;
    private PagedProxy proxy = new PagedProxy(10);
    private boolean mIsOnlyOnePage;

    public LogisticsDataSource(String status) {
        super();
        this.status = status;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<OrderItem>> sender) throws Exception {
        return load(sender, proxy.reset());
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<OrderItem>> sender) throws Exception {
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

    private RequestHandle load(final ResponseSender<List<OrderItem>> sender, final int page) throws Exception {
        StatusListBody body=new StatusListBody();
        body.setPage(page);
        body.setPageSize(proxy.getPageSize());
        body.setStatus(status);

        final Disposable subscription = ApiFactory.myOrderList(body).subscribe(new Consumer<ApiResponse<ListData<OrderItem>>>() {
            @Override
            public void accept(ApiResponse<ListData<OrderItem>> listDataApiResponse) throws Exception {
                if (!proxy.isPageCountSet()) {
                    proxy.setDataCount(listDataApiResponse.getData().getPageInfo().getCountX());
                }
                mIsOnlyOnePage = listDataApiResponse.getData().getPageInfo().getPageCount() == 1;
                List<OrderItem> items = listDataApiResponse.getData().getItems();
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
