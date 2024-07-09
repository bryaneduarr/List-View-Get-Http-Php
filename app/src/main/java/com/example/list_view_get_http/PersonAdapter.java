package com.example.list_view_get_http;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PersonAdapter extends ArrayAdapter<Personas> {
  public PersonAdapter(Context context, List<Personas> persons) {
    super(context, 0, persons);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Personas persona = getItem(position);

    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);
    }

    TextView nombresTextView = convertView.findViewById(R.id.nombresTextView);
    TextView apellidosTextView = convertView.findViewById(R.id.apellidosTextView);
    TextView direccionTextView = convertView.findViewById(R.id.direccionTextView);
    TextView telefonoTextView = convertView.findViewById(R.id.telefonoTextView);
    TextView fotoTextView = convertView.findViewById(R.id.fotoTextView);

    nombresTextView.setText(persona.getNombres());
    apellidosTextView.setText(persona.getApellidos());
    direccionTextView.setText(persona.getDireccion());
    telefonoTextView.setText(persona.getTelefono());
    fotoTextView.setText(persona.getFoto());

    return convertView;
  }
}
