package com.doancuoinam.hostelappdoancuoinam.view.host.fragment.list.listExtends.listBoardingHostel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.doancuoinam.hostelappdoancuoinam.Model.ModelApi.ListandCoutRoom;
import com.doancuoinam.hostelappdoancuoinam.R;
import java.util.List;

public class ListBoardingHostelAdapter extends RecyclerView.Adapter<ListBoardingHostelAdapter.ListBoardingViewHolder> {
    List<ListandCoutRoom> boardingHostList;
    public void setDataBoardingHostel(List<ListandCoutRoom> boardingHostList) {
        this.boardingHostList = boardingHostList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListBoardingHostelAdapter.ListBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_boardinghostel,parent,false);
        return new  ListBoardingHostelAdapter.ListBoardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListBoardingHostelAdapter.ListBoardingViewHolder holder, int position) {
        ListandCoutRoom listandCoutRoom = boardingHostList.get(position);
        holder.onBind(listandCoutRoom);
    }

    @Override
    public int getItemCount() {
      return   boardingHostList != null ? boardingHostList.size() : 0;
    }

    public class ListBoardingViewHolder extends RecyclerView.ViewHolder {
        TextView idBoarding,AddressBoarding,numberRoomIn,numberRoomEmpty;
        public ListBoardingViewHolder(@NonNull View itemView) {
            super(itemView);
            idBoarding = itemView.findViewById(R.id.idBoarding);
            AddressBoarding = itemView.findViewById(R.id.AddressBoarding);
            numberRoomIn = itemView.findViewById(R.id.numberRoomIn);
            numberRoomEmpty = itemView.findViewById(R.id.numberRoomEmpty);
        }
        void onBind(ListandCoutRoom boardingHost){
            idBoarding.setText(String.valueOf(boardingHost.getBoardingHost().getId()));
            AddressBoarding.setText(boardingHost.getBoardingHost().getAddress());
            numberRoomIn.setText(String.valueOf(boardingHost.getBoardingHost().getNumberRoom()));
            numberRoomEmpty.setText(String.valueOf(boardingHost.getCount()));
        }
    }
}
