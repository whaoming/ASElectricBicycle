//package com.wxxiaomi.ming.electricbicycle.core.weight.adapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView.ViewHolder;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.TextView;
//
//import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;
//import com.wxxiaomi.ming.electricbicycle.R;
//
//
//public class PoiSearchResultAdapter  extends RecyclerView.Adapter<ViewHolder>{
//
//	private List<SuggestionInfo> infos;
//	private Context context;
//	private MyPoiSuggrestionResultClickListener lis;
//
//
//	public List<SuggestionInfo> getInfoList(){
//		return infos;
//	}
//	public PoiSearchResultAdapter(Context context,MyPoiSuggrestionResultClickListener poiSuggrestionResultClickListener) {
//		super();
//		infos = new ArrayList<SuggestionInfo>();
//		this.context = context;
//		this.lis = poiSuggrestionResultClickListener;
//	}
//
//
//	public void addInfo(SuggestionInfo info){
//		infos.add(info);
//	}
//
//	public void clear(){
//		infos.clear();
//
//	}
//
//	public void update(){
//		notifyItemChanged(infos.size());
//	}
//
//	@Override
//	public int getItemCount() {
//		return infos.size();
//	}
//
//	@Override
//	public void onBindViewHolder(ViewHolder viewHolder, int position) {
//		SuggestionInfo suggestionInfo = infos.get(position);
//		ItemViewHolder holder = (ItemViewHolder) viewHolder;
//		holder.tv_title.setText(suggestionInfo.key);
//		holder.tv_city.setText(suggestionInfo.city);
//	}
//
//	@Override
//	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//		View view = LayoutInflater.from(context).inflate(R.layout.item_poisuggestionresult,
//				viewGroup, false);
//		return new ItemViewHolder(view);
//	}
//
//	public class ItemViewHolder extends ViewHolder implements OnClickListener{
//		public TextView tv_title;
//		public TextView tv_city;
//
//		public ItemViewHolder(View view) {
//			super(view);
//			tv_title = (TextView) view.findViewById(R.id.tv_title);
//			tv_city = (TextView) view.findViewById(R.id.tv_city);
//			view.setOnClickListener(this);
//		}
//		@Override
//		public void onClick(View v) {
////			Log.i("wang", "getAdapterPosition()="+getAdapterPosition());
////			Log.i("wang", "getItemId="+getItemId());
////			Log.i("wang", "getLayoutPosition()="+getLayoutPosition());
////			Log.i("wang", "getPosition()="+getPosition());
//			lis.click(getAdapterPosition());
//		}
//	}
//
//	public interface MyPoiSuggrestionResultClickListener{
//		void click(int position);
//	}
//
//}
