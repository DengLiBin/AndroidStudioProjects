package com.denglibin.topnews.adapter;

import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.denglibin.topnews.Config.Constant;
import com.denglibin.topnews.MainActivity;
import com.denglibin.topnews.R;
import com.denglibin.topnews.bean.News;
import com.denglibin.topnews.utils.Options;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/4/26.
 */
public class ListNewsAdapter extends BaseAdapter {
    private MainActivity mainActivity;
    private List<News> news;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    /**
     * popWindow 关闭按钮
     */
    private ImageView btn_pop_close;
    private PopupWindow popupWindow;
    LayoutInflater inflater = null;

    public ListNewsAdapter(MainActivity mainActivity, List<News> news) {
        this.mainActivity = mainActivity;
        this.news = news;
        inflater = LayoutInflater.from(mainActivity);
        options = Options.getListOptions();
        initPopWindow();
    }

    @Override
    public int getCount() {
        return news == null ? 0 : news.size();
    }

    @Override
    public News getItem(int position) {
        if (news != null && news.size() != 0) {
            return news.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_list_fragment, null);
            holder.item_layout = (RelativeLayout) view.findViewById(R.id.item_layout);
            holder.comment_layout = (RelativeLayout) view.findViewById(R.id.comment_layout);
            holder.item_title = (TextView) view.findViewById(R.id.item_title);
            holder.item_source = (TextView) view.findViewById(R.id.item_source);
            holder.list_item_local = (TextView) view.findViewById(R.id.list_item_local);
            holder.comment_count = (TextView) view.findViewById(R.id.comment_count);
            holder.publish_time = (TextView) view.findViewById(R.id.publish_time);
            holder.item_abstract = (TextView) view.findViewById(R.id.item_abstract);
            holder.alt_mark = (ImageView) view.findViewById(R.id.alt_mark);
            holder.right_image = (ImageView) view.findViewById(R.id.iv_right);
            holder.item_image_layout = (LinearLayout) view.findViewById(R.id.item_image_layout);
            holder.item_image_0 = (ImageView) view.findViewById(R.id.item_image_0);
            holder.item_image_1 = (ImageView) view.findViewById(R.id.item_image_1);
            holder.item_image_2 = (ImageView) view.findViewById(R.id.item_image_2);
            holder.large_image = (ImageView) view.findViewById(R.id.large_image);
            holder.popicon = (ImageView) view.findViewById(R.id.popicon);
            holder.comment_content = (TextView) view.findViewById(R.id.comment_content);
            holder.right_padding_view = view.findViewById(R.id.right_padding_view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //获取position对应的数据
        News news_item = getItem(position);
        holder.item_title.setText(news_item.getTitle());
        holder.item_source.setText(news_item.getSource());
        holder.comment_count.setText("评论" + news_item.getCommentNum());
        holder.publish_time.setText(news_item.getPublishTime() + "小时前");
        List<String> imgUrlList = news_item.getPicList();
        holder.popicon.setVisibility(View.VISIBLE);
        holder.comment_count.setVisibility(View.VISIBLE);
        holder.right_padding_view.setVisibility(View.VISIBLE);
        if (imgUrlList != null && imgUrlList.size() != 0) {
            if (imgUrlList.size() == 1) {
                holder.item_image_layout.setVisibility(View.GONE);
                //是否是大图
                if (news_item.getIsLarge()) {
                    holder.large_image.setVisibility(View.VISIBLE);
                    holder.right_image.setVisibility(View.GONE);
                    imageLoader.displayImage(imgUrlList.get(0), holder.large_image, options);
                    holder.popicon.setVisibility(View.GONE);
                    holder.comment_count.setVisibility(View.GONE);
                    holder.right_padding_view.setVisibility(View.GONE);
                } else {
                    holder.large_image.setVisibility(View.GONE);
                    holder.right_image.setVisibility(View.VISIBLE);
                    imageLoader.displayImage(imgUrlList.get(0), holder.right_image, options);
                }
            } else {
                holder.large_image.setVisibility(View.GONE);
                holder.right_image.setVisibility(View.GONE);
                holder.item_image_layout.setVisibility(View.VISIBLE);
                imageLoader.displayImage(imgUrlList.get(0), holder.item_image_0, options);
                imageLoader.displayImage(imgUrlList.get(1), holder.item_image_1, options);
                imageLoader.displayImage(imgUrlList.get(2), holder.item_image_2, options);
            }
        } else {
            holder.right_image.setVisibility(View.GONE);
            holder.item_image_layout.setVisibility(View.GONE);
        }
        int markResID = getAltMarkResID(news_item.getMark(), news_item.getCollectStatus());
        if (markResID != -1) {
            holder.alt_mark.setVisibility(View.VISIBLE);
            holder.alt_mark.setImageResource(markResID);
        } else {
            holder.alt_mark.setVisibility(View.GONE);
        }
        //判断该新闻概述是否为空
        if (!TextUtils.isEmpty(news_item.getNewsAbstract())) {
            holder.item_abstract.setVisibility(View.VISIBLE);
            holder.item_abstract.setText(news_item.getNewsAbstract());
        } else {
            holder.item_abstract.setVisibility(View.GONE);
        }
        //判断该新闻是否是特殊标记的，推广等，为空就是新闻
        if (!TextUtils.isEmpty(news_item.getLocal())) {
            holder.list_item_local.setVisibility(View.VISIBLE);
            holder.list_item_local.setText(news_item.getLocal());
        } else {
            holder.list_item_local.setVisibility(View.GONE);
        }
        //判断评论字段是否为空，不为空显示对应布局
        if (!TextUtils.isEmpty(news_item.getComment())) {
            //news.getLocal() != null && 
            holder.comment_layout.setVisibility(View.VISIBLE);
            holder.comment_content.setText(news_item.getComment());
        } else {
            holder.comment_layout.setVisibility(View.GONE);
        }
        //判断该新闻是否已读
        if (!news_item.getReadStatus()) {
            holder.item_layout.setSelected(true);
        } else {
            holder.item_layout.setSelected(false);
        }
        //设置+按钮点击效果
        holder.popicon.setOnClickListener(new popAction(position));
        return view;
    }

    static class ViewHolder {
        RelativeLayout item_layout;
        //title
        TextView item_title;
        //图片源
        TextView item_source;
        //类似推广之类的标签
        TextView list_item_local;
        //评论数量
        TextView comment_count;
        //发布时间
        TextView publish_time;
        //新闻摘要
        TextView item_abstract;
        //右上方TAG标记图片
        ImageView alt_mark;
        //右边图片
        ImageView right_image;
        //3张图片布局
        LinearLayout item_image_layout; //3张图片时候的布局
        ImageView item_image_0;
        ImageView item_image_1;
        ImageView item_image_2;
        //大图的图片的话布局
        ImageView large_image;
        //pop按钮
        ImageView popicon;
        //评论布局
        RelativeLayout comment_layout;
        TextView comment_content;
        //paddingview
        View right_padding_view;
    }

    /**
     * 根据属性获取对应的资源ID
     */
    public int getAltMarkResID(int mark, boolean isfavor) {
        if (isfavor) {
            return R.mipmap.ic_mark_favor;
        }
        switch (mark) {
            case Constant.mark_recom:
                return R.mipmap.ic_mark_recommend;
            case Constant.mark_hot:
                return R.mipmap.ic_mark_hot;
            case Constant.mark_frist:
                return R.mipmap.ic_mark_first;
            case Constant.mark_exclusive:
                return R.mipmap.ic_mark_exclusive;
            case Constant.mark_favor:
                return R.mipmap.ic_mark_favor;
            default:
                break;
        }
        return -1;
    }

    /**
     * 初始化弹出的pop
     */
    private void initPopWindow() {
        View popView = inflater.inflate(R.layout.list_item_pop, null);
        popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.PopMenuAnimation);
        btn_pop_close = (ImageView) popView.findViewById(R.id.btn_pop_close);
    }

    /**
     * 显示popWindow
     */
    public void showPop(View parent, int x, int y, int postion) {
        //设置popwindow显示位置
        popupWindow.showAtLocation(parent, 0, x, y);
        //获取popwindow焦点
        popupWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        if (popupWindow.isShowing()) {

        }
        btn_pop_close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 每个ITEM中more按钮对应的点击动作
     */
    public class popAction implements View.OnClickListener {
        int position;

        public popAction(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            int[] arrayOfInt = new int[2];
            //获取点击按钮的坐标
            v.getLocationOnScreen(arrayOfInt);
            int x = arrayOfInt[0];
            int y = arrayOfInt[1];
            showPop(v, x, y, position);
        }
    }
}
