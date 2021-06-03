package tabelaDeFrutas.visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tabelaDeFrutas.controle.FrutaControle;
import tabelaDeFrutas.modelo.Fruta;
import tabelaDeFrutas.modelo.FrutaDAO;

public class ListarFruta extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel painelFundo;
	private JPanel painelBotoes;
	private JTable tabela;
	private JScrollPane barraRolagem ;
	private JButton btInserir;
	private JButton btExcluir;
	private JButton btEditar;
	private DefaultTableModel modelo = new DefaultTableModel();
	
	public ListarFruta() {
		super("Frutas");
		criaJTable();
		criaJanela();
	}
	
	public void criaJTable() {
		tabela = new JTable(modelo);
		modelo.addColumn("Id");
		modelo.addColumn("Descrição");
		modelo.addColumn("Quantidade");
		tabela.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(10);
		pesquisar(modelo);
		
	}
	
	public void criaJanela() {
		btInserir = new JButton("Inserir");
		btExcluir = new JButton("Excluir");
		btEditar = new JButton("Editar");
		painelBotoes = new JPanel();
		barraRolagem = new JScrollPane(tabela);
		painelFundo = new JPanel();
		painelFundo.setLayout(new BorderLayout());
		painelFundo.add(BorderLayout.CENTER, barraRolagem);
		painelBotoes.add(btInserir);
		painelBotoes.add(btEditar);
		painelBotoes.add(btExcluir);
		painelFundo.add(BorderLayout.SOUTH, painelBotoes);
		
		this.getContentPane().add(painelFundo);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(500, 320));
		this.pack();
		this.setVisible(true);
		btInserir.addActionListener(new BtInserirListener());
		btEditar.addActionListener(new BtEditarListener());
		btExcluir.addActionListener(new BtExcluirListener());
	}
	//Adiciona tuplas e as preenche com os dados presentes na tabela do banco de dados
	public static void pesquisar(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		for(Fruta f : new FrutaControle().getFrutas()) {
			modelo.addRow(new Object[] {f.getId(), f.getDescricao(), f.getQuantidade()});
		}
	}
	
	private class BtInserirListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			InserirFruta ifr = new InserirFruta(modelo);
		}
	}
	
	private class BtEditarListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = -1;
			linhaSelecionada = tabela.getSelectedRow();
			if(linhaSelecionada >= 0) {
				Fruta fruta = new Fruta();
				fruta.setId((int) tabela.getValueAt(linhaSelecionada, 0));
				AtualizarFruta atFruta = new AtualizarFruta(modelo, linhaSelecionada, fruta.getId());
			}
			else {
				JOptionPane.showMessageDialog(null, "É nescessário selecionar uma linha");
			}
		}
	}
	
	private class BtExcluirListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = -1;
			linhaSelecionada = tabela.getSelectedRow();
			if(linhaSelecionada >= 0) {
				int idFruta = (int) tabela.getValueAt(linhaSelecionada, 0);
				new ExcluirFruta(modelo, linhaSelecionada, idFruta);
			}
			else {
				JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha");
			}
		}
	}
}