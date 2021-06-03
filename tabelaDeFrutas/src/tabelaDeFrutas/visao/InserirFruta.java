package tabelaDeFrutas.visao;

import java.awt.BorderLayout;
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

public class InserirFruta extends JFrame{

	private DefaultTableModel modelo;
	private JPanel painelCampos;
	private JPanel painelFundo;
	private JButton btSalvar;
	private JButton btLimpar;
	private JLabel lbDescricao;
	private JLabel lbQuantidade;
	private JTextField txDescricao;
	private JTextField txQuantidade;
	
	public InserirFruta(DefaultTableModel md) {
		super("Frutas");
		criaJanela();
		this.modelo = md;
	}
	
	public void criaJanela() {
		btSalvar = new JButton("Salvar");
		btLimpar = new JButton("Limpar");
		lbDescricao = new JLabel("Nome:");
		lbQuantidade = new JLabel("Quantidade:");
		txDescricao = new JTextField();
		txQuantidade = new JTextField();
		
		painelCampos = new JPanel();
		painelCampos.setLayout(new GridLayout(3,2,2,4));
		painelFundo = new JPanel();
		painelCampos.add(lbDescricao);
		painelCampos.add(txDescricao);
		painelCampos.add(lbQuantidade);
		painelCampos.add(txQuantidade);
		painelCampos.add(btLimpar);
		painelCampos.add(btSalvar);
		painelFundo.add(painelCampos);

		this.getContentPane().add(painelFundo);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(300, 150);
		this.pack();
		this.setVisible(true);
		btSalvar.addActionListener(new BtSalvarListener());
		btLimpar.addActionListener(new BtLimparListener());
	}
	
	private class BtSalvarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Fruta f = new Fruta();
			f.setDescricao(txDescricao.getText());
			f.setQuantidade(Integer.parseInt(txQuantidade.getText()));
			new FrutaControle().inserir(f);
			ListarFruta.pesquisar(modelo);
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
