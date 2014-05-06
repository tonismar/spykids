package br.com.tonismar.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by tonismar on 5/5/14.
 */
public class OcorrenciaAdapter extends BaseAdapter {

    private final Context ctx;
    private final List<Ocorrencia> ocorrencias;

    public OcorrenciaAdapter(Context ctx, List<Ocorrencia> ocorrencias) {
        this.ctx = ctx;
        this.ocorrencias = ocorrencias;
    }

    @Override
    public int getCount() {
        return ocorrencias.size();
    }

    @Override
    public Object getItem(int i) {
        return ocorrencias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ocorrencias.get(i).getId();
    }

    @Override
    public View getView(int pos, View viewAtual, ViewGroup arg2) {

        ViewHolder holder = null;
        if (viewAtual == null){
            LayoutInflater inflater = (LayoutInflater)
                    ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.list_item, null);
            TextView txtDia = (TextView) v.findViewById(R.id.txtdia);
            TextView txtHora = (TextView) v.findViewById(R.id.txthora);
            TextView txtMensagem = (TextView) v.findViewById(R.id.txtmensagem);

            holder = new ViewHolder();
            holder.txtDia = txtDia;
            holder.txtHora = txtHora;
            holder.txtMensagem = txtMensagem;

            v.setTag(holder);
            viewAtual = v;
        } else {
            holder = (ViewHolder) viewAtual.getTag();
        }

        Ocorrencia ocorrencia = ocorrencias.get(pos);
        holder.txtDia.setText(ocorrencia.getDia());
        holder.txtHora.setText(ocorrencia.getHora());
        holder.txtMensagem.setText(ocorrencia.getMensagem());
        holder.ocorrencia = ocorrencia;

        return viewAtual;
    }

    class ViewHolder {
        public TextView txtDia;
        public TextView txtHora;
        public TextView txtMensagem;
        public Ocorrencia ocorrencia = null;
    }
}
