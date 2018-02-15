package jp.techacademy.son.escape;

/**
 * Created by taiso on 2018/02/15.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


class ViewHolder {
    TextView companyTextView;
    TextView blackNameTextView;
    TextView caseTextView;
    TextView dateTextView;
}

public class ArticleDataArrayListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<articleData> articleDataArrayList = new ArrayList<articleData>();

    public ArticleDataArrayListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String mCompanyName = articleDataArrayList.get(position).getCompanyName();
        String mBlackName = articleDataArrayList.get(position).getBlackName();
        String mCase = articleDataArrayList.get(position).getCase();
        String mDate = articleDataArrayList.get(position).getDate();


        ViewHolder holder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            // FolderViewHolder を生成
            holder = new ViewHolder();
            holder.companyTextView = (TextView) convertView.findViewById(R.id.companyTextView);
            holder.blackNameTextView = (TextView) convertView.findViewById(R.id.blackNameTextView);
            holder.caseTextView = (TextView) convertView.findViewById(R.id.caseTextView);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }




        if(mCompanyName!=null){
            holder.companyTextView.setText("会社名  " + mCompanyName);
        }
        if(mBlackName!=null){
            holder.blackNameTextView.setText("社長名　" + mBlackName);
        }
        if(mCase!=null){
            holder.caseTextView.setText("ケース　" + mCase);
        }
        if(mDate!=null){
            holder.dateTextView.setText("発生年　"+ mDate);
        }


        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return articleDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        //return null;
        return articleDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
        //return articleDataArrayList.get(position).getId();
    }
    public void setArticleDataArrayList(ArrayList<articleData> articleDataArrayList){
        this.articleDataArrayList = articleDataArrayList;
    }
}




