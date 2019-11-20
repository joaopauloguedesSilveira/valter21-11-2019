package mesa;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;


public class Cadeira {
    
    private TransformGroup tgTampo;
    private Transform3D t3dTampo;
    private TransformGroup tgPerna1;
    private Transform3D t3dPerna1;
    private TransformGroup tgPerna2;
    private Transform3D t3dPerna2;
    private TransformGroup tgPerna3;
    private Transform3D t3dPerna3;
    private TransformGroup tgPerna4;
    private Transform3D t3dPerna4;
    
    private TransformGroup tgTampoCad;
    private Transform3D t3dTampoCad;
    
    private Box tampo;
    private Box tampoCad;
    private Cylinder perna1;
    private Cylinder perna2;
    private Cylinder perna3;
    private Cylinder perna4;
    
    
    public Cadeira(Vector3f posicao){
        
        Appearance aparenciaTampo = new Appearance();
        Material material = new Material(new Color3f(1.0f,1.0f,0.0f), 
                 new Color3f(0.0f,0.0f,0.0f), 
                 new Color3f(1.0f,1.0f,0.0f), 
                 new Color3f(1.0f,1.0f,1.0f), 
                 60.0f); 
        aparenciaTampo.setMaterial(material);
        
        t3dTampo = new Transform3D();
        t3dTampo.set(posicao);
        tgTampo = new TransformGroup(t3dTampo);
        tampo = new Box(0.7f, 0.1f, 0.7f, aparenciaTampo);
        tgTampo.addChild(tampo);
        
        
        t3dTampoCad = new Transform3D();
        t3dTampoCad.set(new Vector3f(-0.6f, 0.7f, 0.0f));
        
        tgTampoCad = new TransformGroup(t3dTampoCad);
        tampoCad = new Box(0.1f, 0.8f,0.7f,  aparenciaTampo);
        tgTampoCad.addChild(tampoCad);
        
        tgTampo.addChild(tgTampoCad);
        
        tgTampo.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tgTampo.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        
        
        Appearance aparenciaPerna = new Appearance();
        Material materialPerna = new Material(new Color3f(0.4f,0.4f, 0.4f),
                 new Color3f(0.0f, 0.0f, 0.0f),
                 new Color3f(0.4f, 0.4f, 0.4f),
                 new Color3f(1.0f, 1.0f, 1.0f),
                 60.0f);
        aparenciaPerna.setMaterial(materialPerna);
        
        
        t3dPerna1 = new Transform3D();
        t3dPerna1.set(new Vector3f(-0.5f, -0.4f, 0.5f));
        tgPerna1 = new TransformGroup(t3dPerna1);
        perna1 = new Cylinder(0.1f, 0.8f, aparenciaPerna);
        tgPerna1.addChild(perna1);
        
        
        t3dPerna2 = new Transform3D();
        t3dPerna2.set(new Vector3f(0.5f, -0.4f, 0.5f));
        tgPerna2 = new TransformGroup(t3dPerna2);
        perna2 = new Cylinder(0.1f, 0.8f, aparenciaPerna);
        tgPerna2.addChild(perna2);
        
        
        t3dPerna3 = new Transform3D();
        t3dPerna3.set(new Vector3f(-0.5f, -0.4f, -0.5f));
        tgPerna3 = new TransformGroup(t3dPerna3);
        perna3 = new Cylinder(0.1f, 0.8f, aparenciaPerna);
        tgPerna3.addChild(perna3);
        
        
        t3dPerna4 = new Transform3D();
        t3dPerna4.set(new Vector3f(0.5f, -0.4f, -0.5f));
        tgPerna4 = new TransformGroup(t3dPerna4);
        perna4 = new Cylinder(0.1f, 0.8f, aparenciaPerna);
        tgPerna4.addChild(perna4);
        
        tgTampo.addChild(tgPerna1);
        tgTampo.addChild(tgPerna2);
        tgTampo.addChild(tgPerna3);
        tgTampo.addChild(tgPerna4);
    
} 
     public TransformGroup getCadeira(){
        return tgTampo;
    }
    
    public void setTransformacao(Transform3D t3dTemp){
        t3dTampo.mul(t3dTemp);
        tgTampo.setTransform(t3dTampo);
    }
    
    
}
