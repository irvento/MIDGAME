
package main;

import playerz.PVP_dashboard;


public class MainClass {

	public static void main(String[] args) {
            inputs.KeyBindings.load();
            PVP_dashboard pvp_dashboard = new PVP_dashboard();
            pvp_dashboard.setLocationRelativeTo(null);
            pvp_dashboard.setVisible(true);
            
	}

}
