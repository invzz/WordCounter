package models;

import javax.swing.tree.DefaultMutableTreeNode;

import implementations.User;

public interface GUI {

	@SuppressWarnings({ "exports"})
	DefaultMutableTreeNode findNode(DefaultMutableTreeNode root, String search);

	void rmvUsr(String user);

	void addUsr(User user);

	void addLocation(String area);

	void clearTop(String area);

	void addWord(String token, String area, Integer occ);

	void Update(String string);


}
