package tabelaDeFrutas.visao;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import tabelaDeFrutas.controle.FrutaControle;
import tabelaDeFrutas.modelo.Fruta;
import tabelaDeFrutas.modelo.FrutaDAO;

public class AtualizarFruta extends JFrame{

	private DefaultTableModel modelo;
	private JPanel painelFundo;
	private JButton btSalvar;
	private JButton btLimpar;
	private JLabel lbId;
	private JLabel lbDescricao;
	private JLabel lbQuantidade;
	private JTextField txId;
	private JTextField txDescricao;
	private JTextField txQuantidade;
	private Fruta fruta;
	int linhaSelecionada;
	public AtualizarFruta(DefaultTableModel md, int linha, int id) {
		super("Atualizar Fruta");
		criaJanela();
		this.modelo = md;
		this.linhaSelecionada = linha;
		this.fruta = new FrutaDAO().getFrutaById(id);
		this.txId.setText(Integer.toString(fruta.getId()));
		this.txDescricao.setText(fruta.getDescricao());
		this.txQuantidade.setText(Integer.toString(fruta.getQuantidade()));
	}
	
	public void criaJanela() {
		painelFundo = new JPanel(new GridLayout(4, 2, 2, 4));
		lbId = new JLabel("Id:");
		lbDescricao = new JLabel("Descrição");
		lbQuantidade = new JLabel("Quantidade");
		txId = new JTextField();
		txId.setEditable(false);
		txDescricao = new JTextField();
		txQuantidade = new JTextField();
		btSalvar = new JButton("Salvar");
		btLimpar = new JButton("Limpar");
		painelFundo.add(lbId);
		painelFundo.add(txId);
		painelFundo.add(lbDescricao);
		painelFundo.add(txDescricao);
		painelFundo.add(lbQuantidade);
		painelFundo.add(txQuantidade);
		painelFundo.add(btLimpar);
		painelFundo.add(btSalvar);
		
		btSalvar.addActionListener(new BtSalvarListener());
		btLimpar.addActionListener(new BtLimparListener());
		
		this.setSize(300, 150);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().add(painelFundo);
		this.setVisible(true);
	}
	
	private class BtSalvarListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Fruta fruta = new Fruta();
			fruta.setId(Integer.parseInt(txId.getText()));
			fruta.setDescricao(txDescricao.getText());
			fruta.setQuantidade(Integer.parseInt(txQuantidade.getText()));
			new FrutaControle().atualizar(fruta);
			modelo.removeRow(linhaSelecionada);
			modelo.addRow(new Object[] {fruta.getId(), fruta.getDescricao(), fruta.getQuantidade()});
			setVisible(false);
		}
	}
	
	private class BtLimparListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			txDescricao.setText("");
			txQuantidade.setText("");
		}
	}
}
