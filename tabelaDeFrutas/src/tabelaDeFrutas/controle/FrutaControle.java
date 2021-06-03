package tabelaDeFrutas.controle;

import java.util.List;

import tabelaDeFrutas.modelo.Fruta;
import tabelaDeFrutas.modelo.FrutaDAO;

public class FrutaControle {

	private FrutaDAO dao;

	public FrutaDAO getDao() {
		return dao;
	}

	public void setDao(FrutaDAO dao) {
		this.dao = dao;
	}
	
	public void inserir(Fruta fruta) {
		new FrutaDAO().inserir(fruta);
	}
	
	public void atualizar(Fruta fruta) {
		new FrutaDAO().atualizar(fruta);
	}
	
	public void remover(int id) {
		new FrutaDAO().remover(id);
	}
	
	public List<Fruta> getFrutas(){
		return new FrutaDAO().getFrutas();
	}
	
	public Fruta getFrutaByYd(int id) {
		return new FrutaDAO().getFrutaById(id);
	}
}
