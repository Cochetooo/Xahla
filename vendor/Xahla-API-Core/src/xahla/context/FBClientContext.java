package xahla.context;

import xahla.client.graphics.Shader;
import xahla.context.components.Projection;
import xahla.context.components.SnapshotBuffer;
import xahla.core.App;

/**
 * This Client context add a framebuffer for the scene.<br>
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class FBClientContext extends ClientContext {
	
	private SnapshotBuffer frame;
	private Shader frameShader;

	public FBClientContext(App app) {
		super(app);
	}
	
	@Override
	public void post_init() {
		frameShader = new Shader(this, "screen", false);
		frame = new SnapshotBuffer(frameShader, this);
		super.post_init();
	}
	
	@Override
	public void pre_render() {
		frame.pre_render();
		super.pre_render();
	}
	
	@Override
	public void post_render() {
		Projection.resetProjection();
		
		frame.post_render();
		super.post_render();
	}
	
	@Override
	public void dispose() {
		frame.dispose();
		super.dispose();
	}
	
	/** @return The shader that handles the frame buffer of the screen. */
	public Shader getScreenShader() { return frameShader; }

}
