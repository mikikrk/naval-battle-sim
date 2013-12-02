import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.HillHeightMap; // for exercise 2
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.ArrayList;
import java.util.List;
 
/** Sample 10 - How to create fast-rendering terrains from heightmaps,
and how to use texture splatting to make the terrain look good.  */
public class NavalBattleSim extends SimpleApplication {
 
  private TerrainQuad terrain;
  Material mat_terrain;
 
  public static void main(String[] args) {
    NavalBattleSim app = new NavalBattleSim();
    app.start();
  }
 
    protected Geometry player, player2;
  
  @Override
  public void simpleInitApp() {
    flyCam.setMoveSpeed(30);
    
     Box b = new Box(1, 1, 1);
     Box b2 = new Box(1, 1, 2);
        player = new Geometry("blue cube", b);
        Material mat = new Material(assetManager,
          "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        player.setMaterial(mat);
        rootNode.attachChild(player);
        
        player2 = new Geometry("red cube", b2);
        Material mat2 = new Material(assetManager,
          "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        player2.setMaterial(mat2);
        rootNode.attachChild(player2);
 
    /** 1. Create terrain material and load four textures into it. */
    mat_terrain = new Material(assetManager, 
            "Common/MatDefs/Terrain/Terrain.j3md");
 
    /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
//    mat_terrain.setTexture("Alpha", assetManager.loadTexture(
//            "Textures/Sea-Texture-2.jpg"));
 
    /** 1.2) Add sea texture into the red layer (Tex1). */
    Texture sea = assetManager.loadTexture(
            "Textures/Sea-Texture-2.jpg");
    sea.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex1", sea);
    mat_terrain.setFloat("Tex1Scale", 64f);
 
    /** 1.3) Add DIRT texture into the green layer (Tex2) */
//    Texture dirt = assetManager.loadTexture(
//            "Textures/Terrain/splat/dirt.jpg");
//    dirt.setWrap(WrapMode.Repeat);
//    mat_terrain.setTexture("Tex2", dirt);
//    mat_terrain.setFloat("Tex2Scale", 32f);
 
    /** 1.4) Add ROAD texture into the blue layer (Tex3) */
//    Texture rock = assetManager.loadTexture(
//            "Textures/Terrain/splat/road.jpg");
//    rock.setWrap(WrapMode.Repeat);
//    mat_terrain.setTexture("Tex3", rock);
//    mat_terrain.setFloat("Tex3Scale", 128f);
 
    /** 2. Create the height map */
    AbstractHeightMap heightmap = null;
    Texture heightMapImage = assetManager.loadTexture(
            "Textures/Height0001.jpg");
    heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
    heightmap.load();
 
    /** 3. We have prepared material and heightmap. 
     * Now we create the actual terrain:
     * 3.1) Create a TerrainQuad and name it "my terrain".
     * 3.2) A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
     * 3.3) We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
     * 3.4) As LOD step scale we supply Vector3f(1,1,1).
     * 3.5) We supply the prepared heightmap itself.
     */
    int patchSize = 65;
    terrain = new TerrainQuad("my terrain", patchSize, 257, null);//heightmap.getHeightMap()
 
    /** 4. We give the terrain its material, position & scale it, and attach it. */
    terrain.setMaterial(mat_terrain);
    terrain.setLocalTranslation(0, -1, 0);
    terrain.setLocalScale(2f, 1f, 2f);
    rootNode.attachChild(terrain);
 
    /** 5. The LOD (level of detail) depends on were the camera is: */
    TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
    terrain.addControl(control);
  }
  
  /* Use the main event loop to trigger repeating actions. */
    @Override
    public void simpleUpdate(float tpf) {
        // make the player rotate:
        player.rotate(0, 0, -2*tpf);
        player.move(-2*tpf, 0, 0);
        player2.move(0, 0, -2*tpf);
    }
}