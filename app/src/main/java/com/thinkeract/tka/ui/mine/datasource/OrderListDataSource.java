package com.thinkeract.tka.ui.mine.datasource;

import com.shizhefei.mvc.IAsyncDataSource;
import com.shizhefei.mvc.RequestHandle;
import com.shizhefei.mvc.ResponseSender;
import com.thinkeract.tka.common.utils.PagedProxy;
import com.thinkeract.tka.data.api.entity.OrderItem;
import com.thinkeract.tka.data.api.request.ListBody;

import java.util.List;

/**
 * Created by ymh on 2016/12/17.
 */

public class OrderListDataSource implements IAsyncDataSource<List<OrderItem>> {
    private PagedProxy proxy = new PagedProxy(10);
    private boolean mIsOnlyOnePage;

    public OrderListDataSource() {
        super();
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
        ListBody body=new ListBody();
        body.setPage(page);
        body.setPageSize(proxy.getPageSize());

//        final Disposable subscription = ApiFactory.getMyOrderList(body).subscribe(new Consumer<ApiResponse<ListData<OrderItem>>>() {
//            @Override
//            public void accept(ApiResponse<ListData<OrderItem>> listDataApiResponse) throws Exception {
//                if (!proxy.isPageCountSet()) {
//                    proxy.setDataCount(listDataApiResponse.getData().getPageInfo().getCountX());
//                }
//                mIsOnlyOnePage = listDataApiResponse.getData().getPageInfo().getPageCount() == 1;
//                List<OrderItem> items = listDataApiResponse.getData().getItems();
//                if (items == null || items.size() == 0) {
//                    proxy.setReachEnd(true);
//                }
//                sender.sendData(items);
//            }
//
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                sender.sendError(new Exception(throwable));
//            }
//        });
//
        return new RequestHandle() {
            @Override
            public void cancel() {
//                subscription.dispose();
            }

            @Override
            public boolean isRunning() {
                return false;
            }
        };
    }



}
