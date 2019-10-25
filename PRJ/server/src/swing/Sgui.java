package swing;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import implementations.User;

import models.GUI;
import models.Server;

public class Sgui implements GUI {

	protected JFrame frame;
	protected JTextArea logger;
	protected Sgui guiReference = this;
	protected Server server;
	private JScrollPane scTree;
	protected JTree infoTree;
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("Infos");
	private DefaultMutableTreeNode wroot = new DefaultMutableTreeNode("Area");
	private DefaultMutableTreeNode uroot = new DefaultMutableTreeNode("Registered Users");

	// protected usrModel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sgui window = new Sgui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Sgui() {
		// this.dataMap=server.getData();
		initialize();
		server = new Server(guiReference);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frame.setBounds(100, 100, 600, 544);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 2));
		scTree = new JScrollPane();

		// Tree
		infoTree = new JTree();
		scTree.setViewportView(infoTree);
		root.add(wroot);
		root.add(uroot);
		infoTree.setModel(new DefaultTreeModel(root));

		JScrollPane scLogger = new JScrollPane();

		logger = new JTextArea();
		scLogger.setViewportView(logger);
		logger.setEditable(false);
		logger.setForeground(Color.ORANGE);
		logger.setBackground(Color.BLACK);
		logger.setWrapStyleWord(true);
		logger.setLineWrap(true);
		logger.setAutoscrolls(true);

		frame.getContentPane().add(scLogger);
		frame.getContentPane().add(scTree);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				try {
					Registry r = LocateRegistry.getRegistry(8080);
					server.exit(r, server);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void Update(String string) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				logger.append(string + "\n");
			}
		});
	}

	@SuppressWarnings("exports")
	@Override
	public DefaultMutableTreeNode findNode(DefaultMutableTreeNode root, String search) {
		Enumeration<?> nodeEnumeration = root.breadthFirstEnumeration();
		while (nodeEnumeration.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodeEnumeration.nextElement();
			String found = (String) node.getUserObject();
			if (search.equals(found)) {
				return node;
			}
		}
		return null;
	}

	@Override
	public synchronized void rmvUsr(String user) {
		DefaultTreeModel model = (DefaultTreeModel) infoTree.getModel();
		DefaultMutableTreeNode found = findNode((DefaultMutableTreeNode) model.getRoot(), user);
		if (found != null) {
			model.removeNodeFromParent(found);
		}
	}

	@Override
	public synchronized void addUsr(User user) {
		DefaultTreeModel model = (DefaultTreeModel) infoTree.getModel();
		// DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(user.getId());
		node.add(new DefaultMutableTreeNode(user.getName() + " " + user.getSurname()));
		model.insertNodeInto(node, uroot, uroot.getChildCount());
	}

	@Override
	public synchronized void addLocation(String area) {
		DefaultTreeModel model = (DefaultTreeModel) infoTree.getModel();
		DefaultMutableTreeNode areaNode = new DefaultMutableTreeNode(area);
		if (findNode(wroot, area) == null) {
			model.insertNodeInto(areaNode, wroot, wroot.getChildCount());
		}
	}

	@Override
	public synchronized void clearTop(String area) {
		DefaultTreeModel model = (DefaultTreeModel) infoTree.getModel();
		DefaultMutableTreeNode areaNode = findNode(wroot, area);
		Enumeration<TreeNode> bfs = areaNode.children();
		while (bfs.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) bfs.nextElement();
			areaNode.remove(node);
		}
		model.reload(areaNode);
	}

	@Override
	public synchronized void addWord(String token, String area, Integer occ) {
		DefaultTreeModel model = (DefaultTreeModel) infoTree.getModel();
		DefaultMutableTreeNode areaNode = findNode(wroot, area);

		DefaultMutableTreeNode wordNode = new DefaultMutableTreeNode(token);
		DefaultMutableTreeNode node = findNode(areaNode, token);
		if (node != null) {
			clearTop(area);
		}
		wordNode.add(new DefaultMutableTreeNode(occ.toString()));
		model.insertNodeInto(wordNode, areaNode, areaNode.getChildCount());
	}

}
