package com.thinkeract.tka.ui.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.ActionTitleItem;
import com.thinkeract.tka.data.api.entity.EffectItem;
import com.thinkeract.tka.data.api.entity.GoodsItem;
import com.thinkeract.tka.data.api.entity.GoodsList;
import com.thinkeract.tka.data.api.entity.NewsItem;
import com.thinkeract.tka.data.api.entity.TreatmentItem;
import com.thinkeract.tka.ui.AppBarActivity;
import com.thinkeract.tka.ui.home.viewbinder.ActionTitleViewBinder;
import com.thinkeract.tka.ui.home.viewbinder.EffectViewBinder;
import com.thinkeract.tka.ui.home.viewbinder.HorizontalGoodsViewBinder;
import com.thinkeract.tka.ui.home.viewbinder.NewsViewBinder;
import com.thinkeract.tka.ui.home.viewbinder.TreatmentViewBinder;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 行动
 * Created by ymh on 2017/10/15 22:52
 * e-mail:minhengyan@gmail.com
 */

public class ActionActivity extends AppBarActivity{
    private RecyclerView mActionRv;
    private MultiTypeAdapter mAdapter;
    private List<Object> items;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_action;
    }

    @Override
    protected void initView() {
        setTitle(R.string.activity);
        mActionRv = (RecyclerView) findViewById(R.id.actionRv);

        mAdapter = new MultiTypeAdapter();
        mAdapter.register(ActionTitleItem.class,new ActionTitleViewBinder());
        mAdapter.register(EffectItem.class,new EffectViewBinder(this));
        mAdapter.register(TreatmentItem.class,new TreatmentViewBinder(this));
        mAdapter.register(GoodsList.class,new HorizontalGoodsViewBinder(this));
        mAdapter.register(NewsItem.class,new NewsViewBinder(this));

        mActionRv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        mActionRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
//        public static final int YELLOW = 0;
//        public static final int PURPLE = 1;
//        public static final int RED = 2;
//        public static final int BLUE = 3;
        items = new ArrayList<>();
        items.add(new ActionTitleItem("影响评分详细",ActionTitleItem.YELLOW));
        items.add(new EffectItem());

        items.add(new ActionTitleItem("介入性治疗",ActionTitleItem.PURPLE));
        items.add(new TreatmentItem());

        items.add(new ActionTitleItem("非介入性治疗",ActionTitleItem.RED));
        List<GoodsItem>  goodsItems = new ArrayList<>();
        GoodsItem item1 = new GoodsItem();
        item1.setId(R.mipmap.aa);
        item1.setName("救心丸");
        item1.setMinprice(88.0);
        goodsItems.add(item1);

        GoodsItem item2 = new GoodsItem();
        item2.setId(R.mipmap.ab);
        item2.setName("椰树叶");
        item2.setMinprice(18.0);
        goodsItems.add(item2);

        GoodsItem item3 = new GoodsItem();
        item3.setId(R.mipmap.ac);
        item3.setName("住院治疗");
        item3.setMinprice(8888.0);
        goodsItems.add(item3);

        GoodsList goodsList = new GoodsList();
        goodsList.setGoodsItems(goodsItems);

        items.add(goodsList);

        items.add(new ActionTitleItem("自体康复",ActionTitleItem.BLUE));
        List<NewsItem>  newsItems = new ArrayList<>();
        NewsItem itema = new NewsItem();
        itema.setId(R.mipmap.ba);
        itema.setTitle("过度减肥可能会影响心脏功能");
        itema.setSubTitle("对单纯的诽谤人群来说，每个月减重3-4斤是比较科学而安全的");
        items.add(itema);

        NewsItem itemb = new NewsItem();
        itemb.setId(R.mipmap.bc);
        itemb.setTitle("谈“癌”色变？带你认知癌症真相");
        itemb.setSubTitle("年纪轻轻的姑娘，竟然得了乳腺癌");
        items.add(itemb);

        NewsItem itemc = new NewsItem();
        itemc.setId(R.mipmap.bd);
        itemc.setTitle("如何让胃不再痛？做好6件事");
        itemc.setSubTitle("上班族10个中就有9个有胃病，这是为什么呢");
        items.add(itemc);

        mAdapter.setItems(items);
        mAdapter.notifyDataSetChanged();
    }
}
