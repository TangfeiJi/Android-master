package com.project.movice.modules.home.adappter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.movice.R;
import com.project.movice.modules.home.base.BeanLoan;

import java.util.List;

public class ProTwo_adapter extends BaseQuickAdapter<BeanLoan.LoanDayObject, BaseViewHolder> {
    private Context mContext;

    public ProTwo_adapter(Context context, @Nullable List<BeanLoan.LoanDayObject> data) {
        super(R.layout.item_protwo, data);
        mContext = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, BeanLoan.LoanDayObject item) {
        TextView tv_content = helper.getView(R.id.tv_content);
        tv_content.setText(item.getDay() + " Hari");
        helper.addOnClickListener(R.id.tv_content);
          if(item.isSelect()){
              tv_content.setBackgroundResource(R.drawable.pro_two_item_selset);
              tv_content.setTextColor(mContext.getResources().getColor(R.color.white));
          }else{
              tv_content.setBackgroundResource(R.drawable.pro_two_item_not);
              tv_content.setTextColor(mContext.getResources().getColor(R.color.auxiliary));
          }
          if(item.getEnable().equals("0")){
              tv_content.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
          }

    }
}
