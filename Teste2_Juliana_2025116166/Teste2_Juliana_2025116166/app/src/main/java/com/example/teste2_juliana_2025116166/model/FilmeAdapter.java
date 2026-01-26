package com.example.teste2_juliana_2025116166.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teste2_juliana_2025116166.DetalhesFilmeActivity;
import com.example.teste2_juliana_2025116166.R;

import java.util.List;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder> {

    private List<Filme> filmes;

    public FilmeAdapter(List<Filme> filmes) {
        this.filmes = filmes;
    }

    static class FilmeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvRealizador, tvActor, tvTipo, tvDuracao, tvAno;
        FilmeViewHolder(View view) {
            super(view);
            tvTitulo = view.findViewById(R.id.tv_titulo);
            tvRealizador = view.findViewById(R.id.tv_realizador);
            tvActor = view.findViewById(R.id.tv_actor);
            tvDuracao = view.findViewById(R.id.tv_duracao);
            tvAno = view.findViewById(R.id.tv_ano);
        }
    }

    @NonNull
    @Override
    public FilmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_filme, parent, false);
        return new FilmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmeViewHolder holder, int position) {
        Filme filme = filmes.get(position);
        holder.tvTitulo.setText(filme.getTitulo());
        holder.tvRealizador.setText("Filme de: " + filme.getRealizador());
        holder.tvActor.setText("Com: " + filme.getActorPrincipal());
        holder.tvDuracao.setText("Duração: " + filme.getDuracao());
        holder.tvAno.setText("Ano: " + filme.getAno());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Intent i = new Intent(c, DetalhesFilmeActivity.class);
                i.putExtra(DetalhesFilmeActivity.VALUE_ID, filme.getId());
                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }


}
