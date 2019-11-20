package mesa;

import com.sun.j3d.utils.geometry.ColorCube;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Locale;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class GrafoCena extends JFrame implements KeyListener {
    
    /*Universo Virtual*/
    private VirtualUniverse universo;
    /*Ponto de referência no mundo virtual - localização dos objetos)*/
    private Locale locale;
    /*Objeto que faz a ligação entre o universo virtual e a janela*/
    private Canvas3D canvas3D;
    /*Objetos que fazem parte do subgrafo de contexto*/
    private ColorCube cubo;
    /*Variável que controla o estilo da tela: true (modo FullScreen), false (modo janela)*/
    private TransformGroup tgCamera;
    private Transform3D t3dCamera;
    
    private Mesa mesa;
    private Cadeira cadeira1;
    private Cadeira cadeira2;
    private Cadeira cadeira3;
    private Cadeira cadeira4;
    boolean screenMode = false;
    
    
    public GrafoCena() {
        /*Utilização de um objeto Dimension para obter as configurações da tela*/
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        /*Ajusta o tamanho da janela*/
        this.setSize(dimension.width, dimension.height);

        /*Retira as bordas da janela, criando o modo FullScreen*/
        if (screenMode) {
            this.setUndecorated(true);
        } else {
            this.setTitle("UNIFOR - Lição 01");
        }

        /*Trata os eventos do teclado*/
        addKeyListener(this);

        /**
         * *********************** PROGRAMAÇÃO EM JAVA3D ***************
         */
        /*Cria o universo virtual*/
        universo = new VirtualUniverse();
        /*Cria o Locale e anexa-o ao mundo virtual*/
        locale = new Locale(universo);

        /*Obtém caracteristicas do dispositivo gráfico utilizado: monitor*/
        GraphicsConfigTemplate3D g3d = new GraphicsConfigTemplate3D();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getBestConfiguration(g3d);

        /*Cria o objeto canvas3D*/
        canvas3D = new Canvas3D(gc);
        /*Ajusta a dimensao do canvas 3D (mesmo tamanho da janela)*/
        canvas3D.setSize(dimension.width, dimension.height);
        /*Adicionando o subgrafo de visualização*/
        locale.addBranchGraph(grafoVisualizacao());
        /*adicionando o subrafo de contexto*/
        locale.addBranchGraph(grafoContexto());

        /**
         * ************************ PROGRAMAÇÃO JAVA *******************
         */
        /*Adiciona o canvas3D ao tratador de eventos do teclado*/
        canvas3D.addKeyListener(this);
        /*Adiciona o canvas3D a janela e o exibe*/
        this.getContentPane().add(canvas3D);
        addWindowListener(new WindowAdapter() {
          public void WindowsClosing(WindowEvent e){
              System.exit(0);
          }
        });
        this.show();
    }
    
     /*Método que cria o subgrafo de visualização*/
    public BranchGroup grafoVisualizacao() {
        /*Cria um objeto BranchGroup*/
        BranchGroup visualBG = new BranchGroup();
        /*Cria um objeto View*/
        View visual = new View();
        /*Cria um objeto ViewPlataform*/
        ViewPlatform visualPlataform = new ViewPlatform();
        /*Cria um objeto PhysicalBody*/
        PhysicalBody physicalBody = new PhysicalBody(new Point3d(0, 0, 0), new Point3d(0, 0, 0));
        /*Cria um objeto PhysicalEnvironment*/
        PhysicalEnvironment ambiente = new PhysicalEnvironment();

        /*Adiciona vewPlatform ao view*/
        visual.attachViewPlatform(visualPlataform);
        /*Adiciona physicalBody ao view*/
        visual.setPhysicalBody(physicalBody);
        /*Adiciona physicalEnvironment ao view*/
        visual.setPhysicalEnvironment(ambiente);
        /*Adiciona canvas ao view*/
        visual.addCanvas3D(canvas3D);

        /*Cria um objeto Transform3D */
        Transform3D transform3D = new Transform3D();
        /*Configura o Transform3D com o Vector3f*/
        transform3D.set(new Vector3f(0.0f, 0.0f, 10.0f));
        /*Cria um objeto TransformGroup*/
        TransformGroup visualPlatformTG = new TransformGroup(transform3D);
        /*Adiciona o ViewPlatform ao TransformGroup*/
        visualPlatformTG.addChild(visualPlataform);

        /*Adiciona o TransformGroup ao BranchGroup*/
        visualBG.addChild(visualPlatformTG);

        /*Retorna o BranchGroup contendo o subgrafo de visualização*/
        return visualBG;
    }

    /*Metodo que cria o subgrafo de contexto*/
    public BranchGroup grafoContexto(){
        //Cria o branchgroup do subgrafo de contexto
        BranchGroup subGrafoContexto = new BranchGroup();
        
        //Cria um "bounds"
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
        
        // Especifica as luzes do "ambiente"
        Color3f corLuz = new Color3f(0.9f, 0.9f, 0.9f);
        Vector3f direcaoLuz = new Vector3f(1.0f, 1.0f, -1.0f);
        DirectionalLight luzDir = new DirectionalLight(corLuz, direcaoLuz);
        luzDir.setInfluencingBounds(bounds);
        
        Color3f corAmb = new Color3f(0.2f, 0.2f, 0.2f);
        AmbientLight luzAmb = new AmbientLight(corAmb);
        luzAmb.setInfluencingBounds(bounds);
        
        subGrafoContexto.addChild(luzAmb);
        subGrafoContexto.addChild(luzDir);
        
        
        mesa = new Mesa(new Vector3f(0.0f,0.0f,0.0f));
        subGrafoContexto.addChild(mesa.getMesa());
        
        
        cadeira1 = new Cadeira(new Vector3f(-1.6f,-0.4f,0.1f));
        mesa.setCadeira(cadeira1.getCadeira());
        
        Transform3D t3dcadeira2 = new Transform3D();
        cadeira2 = new Cadeira(new Vector3f(1.6f,-0.4f,0.1f));
        mesa.setCadeira(cadeira2.getCadeira());
        
        t3dcadeira2.rotY(Math.toRadians(-180));
        cadeira2.setTransformacao(t3dcadeira2);
        
        Transform3D t3dcadeira3 = new Transform3D();
        cadeira3 = new Cadeira(new Vector3f(0.0f,-0.4f,0.9f));
        mesa.setCadeira(cadeira3.getCadeira());
        
        t3dcadeira3.rotY(Math.toRadians(90));
        cadeira3.setTransformacao(t3dcadeira3);
        
        Transform3D t3dcadeira4 = new Transform3D();
        cadeira4 = new Cadeira(new Vector3f(0.0f,-0.4f,-1.0f));
        mesa.setCadeira(cadeira4.getCadeira());
        
        t3dcadeira4.rotY(Math.toRadians(-90));
        cadeira4.setTransformacao(t3dcadeira4);
        
        
        
        //Retorna o BranchGroup contendo o subgrafo de contexto
        return subGrafoContexto;
        
        
    }    

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(1);
        }
        
        if(e.getKeyCode() == KeyEvent.VK_W){
            Transform3D t3dTempo = new Transform3D();
            t3dTempo.rotX(Math.toRadians(20));
            mesa.setTransformacao(t3dTempo);
        }
        
        if(e.getKeyCode() == KeyEvent.VK_S){
            Transform3D t3dTempo = new Transform3D();
            t3dTempo.rotX(Math.toRadians(-20));
            mesa.setTransformacao(t3dTempo);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    
}
