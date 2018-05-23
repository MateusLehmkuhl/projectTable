package projectTable;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tabela extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel painelFundo;
	private JPanel painelBotoes;
	private JPanel painelBotoes2;
	private JTable tabela;
	private JScrollPane barraRolagem;
	private JButton btInserir;
	private JButton btExcluir;
	private JButton btSalvar;
	private JButton btAtualizar;
	private JButton btEntrada;
	private JButton btSaida;
	private JButton btArquivo;
	private DefaultTableModel modelo = new DefaultTableModel();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	String dataDeHoje;

	public Tabela() {
		super("Ponto");
		criaJTable();
		criaJanela();
	}

	public void criaJanela() {
		btInserir = new JButton("Inserir");
		btExcluir = new JButton("Excluir");
		btSalvar = new JButton("Salvar");
		btAtualizar = new JButton("Atualizar");
		btEntrada = new JButton("Entrada");
		btSaida = new JButton("Saida");
		btArquivo = new JButton("Arquivo");

		painelBotoes = new JPanel();
		painelBotoes2 = new JPanel();
		barraRolagem = new JScrollPane(tabela);
		painelFundo = new JPanel();
		painelFundo.setLayout(new BorderLayout());
		painelFundo.add(BorderLayout.CENTER, barraRolagem);
		painelBotoes.add(btInserir);
		painelBotoes.add(btSalvar);
		painelBotoes.add(btExcluir);
		painelBotoes.add(btArquivo);

		painelBotoes2.add(btEntrada);
		painelBotoes2.add(btSaida);
		painelBotoes2.add(btAtualizar);

		painelFundo.add(BorderLayout.SOUTH, painelBotoes2);
		painelFundo.add(BorderLayout.NORTH, painelBotoes);

		getContentPane().add(painelFundo);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 320);
		setVisible(true);
		btInserir.addActionListener(new BtInserirListener());
		btExcluir.addActionListener(new BtExcluirListener());
		btAtualizar.addActionListener(new BtAtualizarListener());
		btSalvar.addActionListener(new BtSalvarListener());
		btEntrada.addActionListener(new BtEntradaListener());
		btSaida.addActionListener(new BtSaidaListener());
		btArquivo.addActionListener(new BtArquivoListener());
	}

	private void criaJTable() {
		tabela = new JTable(modelo);
		modelo.addColumn("Data");
		modelo.addColumn("Hora");
		modelo.addColumn("E/S");
		tabela.getColumnModel().getColumn(1).setPreferredWidth(25);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(10);

		atualizar(modelo);
	}

	public static void atualizar(DefaultTableModel modelo) {
		modelo.setNumRows(0);
		Arquivos arq = new Arquivos();
		for (String c : arq.ler()) {
			modelo.addRow(new Object[] { c.substring(0, 10), c.substring(11, 16), c.substring(17) });
		}
	}

	public void salvarLista(DefaultTableModel modelo) {
		Arquivos arq = new Arquivos();
		arq.escrever("", false);
		for (int i = 0; i < modelo.getRowCount(); i++) {
			for (int j = 0; j < 3; j++) {
				arq.escrever((String) tabela.getValueAt(i, j), true);
				arq.escrever(" ", true);
			}
			arq.escrever("\n", true);
		}
	}

	private class BtInserirListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			modelo.addRow(new Object[] { "          ", "       ", " " });
		}
	}

	private class BtEntradaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Arquivos arquivo = new Arquivos();
			dataDeHoje = dateFormat.format(new Date());
			arquivo.escrever(dataDeHoje + " E\n", true);
			atualizar(modelo);
		}
	}

	private class BtSaidaListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Arquivos arquivo = new Arquivos();
			dataDeHoje = dateFormat.format(new Date());
			arquivo.escrever(dataDeHoje + " S\n", true);
			atualizar(modelo);
		}
	}

	private class BtAtualizarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			atualizar(modelo);
		}
	}

	private class BtArquivoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Arquivos arquivo = new Arquivos();
			JFileChooser fileChooser = new JFileChooser();
			int retorno = fileChooser.showOpenDialog(null);

			if (retorno == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String f = file.toString();
				arquivo.setFilename(f);
			} else {
				System.err.println("Não foi possível selecionar o arquivo");
				return;
			}
			atualizar(modelo);
		}
	}

	private class BtSalvarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			salvarLista(modelo);
		}
	}

	private class BtExcluirListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int linhaSelecionada = -1;
			linhaSelecionada = tabela.getSelectedRow();
			if (linhaSelecionada >= 0) {
				modelo.removeRow(linhaSelecionada);
				salvarLista(modelo);
			} else {
				JOptionPane.showMessageDialog(null, "É necesário selecionar uma linha.");
			}
		}
	}

	public static void main(String[] args) {
		Tabela lc = new Tabela();
		lc.setVisible(true);
	}
}