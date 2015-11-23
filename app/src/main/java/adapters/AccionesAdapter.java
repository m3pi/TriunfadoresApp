package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import imerosa.triunfadoresapp.R;
import models.SolicitudPrestamo;
import models.VentaAccion;

/**
 * Created by mompi3p on 23/11/2015.
 */
public class AccionesAdapter extends ArrayAdapter<VentaAccion> {
    private int resource;

    public AccionesAdapter(Context context, int resource, List<VentaAccion> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(resource, parent, false);

        VentaAccion ventaAccion = getItem(position);

        ((TextView)convertView.findViewById(R.id.tv_Fecha)).setText(ventaAccion.FechaVenta);
        ((TextView)convertView.findViewById(R.id.tv_Estado)).setText(ventaAccion.Estado);
        ((TextView)convertView.findViewById(R.id.tv_Total)).setText(ventaAccion.Total);

        return convertView;
    }
}
