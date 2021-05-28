package view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class CarrinhoCompra extends JFrame {
	private JTextField txtCodigo;
	private JTextField txtProduto;
	private JTextField txtValor;
	private JTextField txtQuantidade;
	private JLabel lblStatus;
	private JButton btnSearch;
	private JButton btnUpdate;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CarrinhoCompra frame = new CarrinhoCompra();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CarrinhoCompra() {
		setSize(new Dimension(410, 415));
		setIconImage(Toolkit.getDefaultToolkit().getImage(CarrinhoCompra.class.getResource("/icones/favicon1.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();

			}
		});
		setBackground(SystemColor.controlHighlight);
		setTitle("Carrinho de compra");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtCodigo = new JTextField();
		txtCodigo.setToolTipText("C\u00F3digo do produto");
		txtCodigo.setBounds(117, 37, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		txtProduto = new JTextField();
		txtProduto.setEditable(false);
		txtProduto.setBounds(117, 68, 146, 20);
		contentPane.add(txtProduto);
		txtProduto.setColumns(10);

		JLabel lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(32, 40, 43, 14);
		contentPane.add(lblCodigo);

		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(32, 70, 46, 14);
		contentPane.add(lblProduto);

		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(32, 128, 46, 14);
		contentPane.add(lblValor);

		txtValor = new JTextField();
		txtValor.setBounds(117, 125, 86, 20);
		contentPane.add(txtValor);
		txtValor.setColumns(10);

		JLabel lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setBounds(32, 102, 78, 14);
		contentPane.add(lblQuantidade);

		txtQuantidade = new JTextField();
		txtQuantidade.setToolTipText("Quantidade de produto");
		txtQuantidade.setBounds(117, 99, 86, 20);
		contentPane.add(txtQuantidade);
		txtQuantidade.setColumns(10);

		lblStatus = new JLabel("");
		lblStatus.setToolTipText("Status da conex\u00E3o");
		lblStatus.setIcon(new ImageIcon(CarrinhoCompra.class.getResource("/icones/conection.png")));
		lblStatus.setBounds(169, 270, 48, 48);
		contentPane.add(lblStatus);

		btnSearch = new JButton("");
		btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		btnSearch.setIcon(new ImageIcon(CarrinhoCompra.class.getResource("/icones/search.png")));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarId();
			}
		});
		btnSearch.setToolTipText("Pesquisar");
		btnSearch.setBounds(213, 11, 48, 48);
		contentPane.add(btnSearch);

		btnLimpar = new JButton("");
		btnLimpar.setToolTipText("Limpar campos");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}

			private void limpar() {
				txtCodigo.setText(null);
				txtProduto.setText(null);
				txtQuantidade.setText(null);
				txtValor.setText(null);
			}
		});
		btnLimpar.setIcon(new ImageIcon(CarrinhoCompra.class.getResource("/icones/delete.png")));
		btnLimpar.setBounds(49, 203, 48, 48);
		contentPane.add(btnLimpar);

		btnSobre = new JButton("Sobre");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"Carrinho de compra ver. BETA\nDevelopers: \nJonathan S. & Paloma K.", "Sobre",
						JOptionPane.DEFAULT_OPTION);
			}
		});

		btnSobre.setToolTipText("Desenvolvedores");
		btnSobre.setBounds(148, 329, 89, 23);
		contentPane.add(btnSobre);

		btnAdd = new JButton("");
		btnAdd.setIcon(new ImageIcon(CarrinhoCompra.class.getResource("/icones/add.png")));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirProduto();
			}
		});
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setToolTipText("Adicionar seu produto ao Carrinho");
		btnAdd.setBounds(130, 203, 48, 48);
		contentPane.add(btnAdd);

		JButton btnUpdate = new JButton("");
		btnUpdate.setToolTipText("Atualizar produto");
		btnUpdate.setIcon(new ImageIcon(CarrinhoCompra.class.getResource("/icones/update.png")));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarInfos();
			}
		});
		btnUpdate.setBounds(277, 203, 48, 48);
		contentPane.add(btnUpdate);

		JButton btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarProduto();
			}
		});
		// btnDelete.setEnabled(false);
		btnDelete.setIcon(new ImageIcon(CarrinhoCompra.class.getResource("/icones/deletar.png")));
		btnDelete.setBorder(null);
		btnDelete.setBackground(SystemColor.control);
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setToolTipText("Deletar produto");
		btnDelete.setBounds(200, 203, 48, 48);
		contentPane.add(btnDelete);
	}

	DAO dao = new DAO();
	private JButton btnLimpar;
	private JButton btnSobre;
	private JButton btnAdd;

	private void status() {
		try {
			Connection con = dao.conectar();
			if (con != null) {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
				btnSearch.setEnabled(true);

			} else

			{
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void selecionarId() {
		String search = "select * from carrinho where codigo = ?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(search);
			pst.setString(1, txtCodigo.getText());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				txtCodigo.setText(rs.getString(1));
				txtProduto.setText(rs.getString(2));
				txtQuantidade.setText(rs.getString(3));
				txtValor.setText(rs.getString(4));
				txtProduto.setEditable(true);
			} else {
				JOptionPane.showMessageDialog(null, "Produto Inexistente");
				txtProduto.setEditable(true);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void alterarInfos() {
		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com o nome do produto");
		} else if (txtQuantidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha com a quantidade");
		} else {
			String update = "update carrinho set produto=?,quantidade=?,valor=? where codigo=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtProduto.getText());
				pst.setString(2, txtQuantidade.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtCodigo.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Produto selecionado atualizado");
				limcamAut();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void inserirProduto() {
		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Campo Obrigatório");
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Campo Obrigatório");
		}

		String create = "insert into carrinho (produto, quantidade, valor) value (?,?,?)";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, txtProduto.getText());
			pst.setString(2, txtValor.getText());
			pst.setString(3, txtQuantidade.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Produto adicionado");
			con.close();
			limcamAut();
		} catch (Exception e) {
			System.out.println(e);
			
		}
	}

	private void deletarProduto() {
		String delete = "delete from carrinho where codigo=?";
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtCodigo.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Produto excluido com sucesso");
				limcamAut();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	private void limcamAut() {
		txtCodigo.setText(null);
		txtProduto.setText(null);
		txtQuantidade.setText(null);
		txtValor.setText(null);
		btnAdd.setEnabled(false);
		btnUpdate.setEnabled(false);
		//btnDelete.setEnabled(false);
		btnSearch.setEnabled(true);
		}
	}

