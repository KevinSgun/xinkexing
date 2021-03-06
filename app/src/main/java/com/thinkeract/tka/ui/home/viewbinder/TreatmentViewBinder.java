/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thinkeract.tka.ui.home.viewbinder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkeract.tka.R;
import com.thinkeract.tka.data.api.entity.TreatmentItem;

import me.drakeet.multitype.ItemViewBinder;

/**
 * @author ymh
 */
public class TreatmentViewBinder extends ItemViewBinder<TreatmentItem, TreatmentViewBinder.ViewHolder> {

    private Activity mContext;

    public TreatmentViewBinder(Activity activity){
        mContext = activity;
    }

    @NonNull @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_treatment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TreatmentItem item) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

//        private @NonNull final RelativeLayout mHealthOptionLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
//            mHealthOptionLayout = (RelativeLayout) itemView.findViewById(R.id.healthOptionLayout);
        }
    }
}
