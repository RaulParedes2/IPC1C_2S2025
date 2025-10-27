/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.*;
import Controlador.Bitacora;
import Modelo.*;
import Modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtCodigo;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Sancarlista Shop - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JLabel lblTitulo = new JLabel("Inicio de Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        txtCodigo = new JTextField();
        txtCodigo.setBorder(BorderFactory.createTitledBorder("Código de usuario"));
        txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));

        btnLogin = new JButton("Ingresar");
        btnLogin.addActionListener(e -> autenticar());

        add(lblTitulo);
        add(txtCodigo);
        add(txtPassword);
        add(btnLogin);
    }

    private void autenticar() {
        String codigo = txtCodigo.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();

        Usuario user = null;

        // 🔹 1. Comprobar si es el ADMIN inicial
        if (codigo.equalsIgnoreCase("admin") && password.equals("IPC1C")) {
            user = new Administrador("admin", "Administrador", "M", "IPC1C");
            JOptionPane.showMessageDialog(this, "Bienvenido administrador inicial.");
            abrirMenu(user);
            
            Bitacora.registrar(
                "ADMIN",
                "admin",
                "INICIO",
                "EXITOSA",
                    " " 
        );
            return;
            
            
        }

        // 🔹 2. Buscar entre VENDEDORES registrados
        for (Vendedores v : Vendedor.getVendedores()) {
            if (v != null && v.getCodigo().equalsIgnoreCase(codigo)
                    && v.getPassword().equals(password)) {
                user = v;
                break;
            }
        }

        // 🔹 3. Buscar entre CLIENTES registrados
        if (user == null) {
            for (Cliente c : Clientes.getClientes()) {
                if (c != null && c.getCodigo().equalsIgnoreCase(codigo)
                        && c.getPassword().equals(password)) {
                    user = c;
                    break;
                }
            }
        }

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + user.getNombre());
            abrirMenu(user);
            
            Bitacora.registrar(
                "USUARIO: "+ user.getNombre(),
                "CODIGO: "+user.getCodigo(),
                "INICIO",
                "EXITOSA",
                    " " 
        );
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
            
            Bitacora.registrar(
                "USUARIO: "+ user.getNombre(),
                "CODIGO: "+user.getCodigo(),
                "INICIO",
                "FALLIDA",
                    " " 
        );
        }
    }
    
    

    private void abrirMenu(Usuario u) {
        dispose();
        if (u instanceof Administrador) {
            new MenuAdmon().setVisible(true);
        } else if (u instanceof Vendedores) {
            new MenuVendedor((Vendedores) u).setVisible(true);
        } else if (u instanceof Cliente) {
            new MenuCliente((Cliente) u).setVisible(true);
        }
    }
    
}
