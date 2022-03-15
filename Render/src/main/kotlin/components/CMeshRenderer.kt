package components

import Component
import graphics.opengl.GLShader
import graphics.opengl.GLTexture
import objects.Entity

open class CMeshRenderer(obj: Entity, protected val drawMode: Int, protected val shader: GLShader, protected val texture: GLTexture) : Component(obj, "CMeshRenderer") {



}