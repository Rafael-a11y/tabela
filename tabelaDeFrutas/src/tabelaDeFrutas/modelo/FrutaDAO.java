package tabelaDeFrutas.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class FrutaDAO {

	private final String INSERT = "INSERT INTO FRUTA (DESCRICAO, QUANTIDADE) VALUES (?,?);";
	private final String UPDATE = "UPDATE FRUTA SET DESCRICAO=?, QUANTIDADE=? WHERE ID = ?";
	private final String DELETE = "DELETE FROM FRUTA WHERE ID =?";
	private final String LIST = "SELECT * FROM FRUTA";
	private final String LISTBYID = "SELECT * FROM FRUTA WHERE ID=?";

	public void inserir(Fruta fruta) {
		if (fruta != null) {
			Connection conn = null;
			try {
				conn = FabricaConexao.getConexao();
				PreparedStatement pstm;
				pstm = conn.prepareStatement(INSERT);

				pstm.setString(1, fruta.getDescricao());
				pstm.setInt(2, fruta.getQuantidade());
				pstm.execute();
				JOptionPane.showMessageDialog(null, "Fruta cadastrada com sucesso");
				FabricaConexao.fechaConexao(conn, pstm);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir fruta no banco de"
						+ "dados " + e.getMessage());
			}
		} else {
			System.out.println("A fruta enviada por parâmetro está vazia");
		}
	}

	public void atualizar(Fruta fruta) {
		if (fruta != null) {
			Connection conn = null;
			try {
				conn = FabricaConexao.getConexao();
				PreparedStatement pstm;
				pstm = conn.prepareStatement(UPDATE);

				pstm.setString(1, fruta.getDescricao());
				pstm.setInt(2, fruta.getQuantidade());
				pstm.setInt(3, fruta.getId());
				pstm.execute();
				JOptionPane.showMessageDialog(null, "Fruta alterada com sucesso");
				FabricaConexao.fechaConexao(conn);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar fruta no banco de"
						+ " dados " + e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null, "A fruta enviada por parâmetro está vazia");
		}


	}

	public void remover(int id) {
		Connection conn = null;
		try {
			conn = FabricaConexao.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(DELETE);

			pstm.setInt(1, id);

			pstm.execute();
			FabricaConexao.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Fruta excluída com sucesso.");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir fruta do banco de"
					+ "dados " + e.getMessage());
		}
	}

	public List<Fruta> getFrutas() {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Fruta> frutas = new ArrayList<Fruta>();
		try {
			conn = FabricaConexao.getConexao();
			pstm = conn.prepareStatement(LIST);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Fruta fruta = new Fruta();

				fruta.setId(rs.getInt("id"));
				fruta.setDescricao(rs.getString("descricao"));
				fruta.setQuantidade(rs.getInt("quantidade"));
				
				frutas.add(fruta);
			}
			FabricaConexao.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar frutas" + e.getMessage());
		}
		return frutas;
	}

	public Fruta getFrutaById(int id) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Fruta fruta = new Fruta();
		try {
			conn = FabricaConexao.getConexao();
			pstm = conn.prepareStatement(LISTBYID);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			while (rs.next()) {
				fruta.setId(rs.getInt("id"));
				fruta.setDescricao(rs.getString("descricao"));
				fruta.setQuantidade(rs.getInt("quantidade"));
			}
			FabricaConexao.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar frutas" + e.getMessage());
		}
		return fruta;
	}
}
