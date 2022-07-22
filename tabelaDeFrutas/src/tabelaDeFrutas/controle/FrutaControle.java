package tabelaDeFrutas.controle;

import java.util.List;

import tabelaDeFrutas.modelo.Fruta;
import tabelaDeFrutas.modelo.DAO;

public class FrutaControle {

	private DAO<Fruta> dao;

	public DAO<Fruta> getDao() {
		return dao;
	}

	public void setDao(DAO<Fruta> dao) {
		this.dao = dao;
	}
	
	public void create(Fruta fruta) {
		new DAO<>().persistirDeFormaAtomica(fruta).fecharEntityManager();
	}
	
	public void update(Fruta fruta) {
		this.setDao(new DAO<>());
		this.dao.comecarTransacao().alterarEntidade(fruta).commitarTransacao().fecharEntityManager();
	}
	
	public void remove(int id) {
		this.setDao(new DAO<>(Fruta.class));
		Fruta fruta = this.dao.obterPorId(id);
		dao.comecarTransacao().removerEntidade(fruta).commitarTransacao().fecharEntityManager();
	}
	
	public List<Fruta> getFrutas(){
		
		this.setDao(new DAO<>(Fruta.class));
		return dao.obterTodos();
	}
	
	public Fruta getFrutaByYd(int id) {
		this.setDao(new DAO<>(Fruta.class));
		return dao.obterPorId(id);
	}
}
