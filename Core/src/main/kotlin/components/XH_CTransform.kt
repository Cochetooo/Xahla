package components

import XH_Component
import XH_Object
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f

/** Transform component: Contains position, rotation and scaling of an object.
 * Copyright (C) Xahla - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Alexis Cochet <alexis.cochetooo@gmail.com>, October 2021
 */
class XH_CTransform(obj: XH_Object, val position: Vector3f = Vector3f(), val rotation: Quaternionf = Quaternionf(), val scale: Vector3f = Vector3f(), name: String = "Transform")
    : XH_Component(obj, name) {

    fun toMatrix(): Matrix4f {
        val translationMatrix = Matrix4f().translate(position)
        val rotationMatrix = Matrix4f().rotate(rotation)
        val scaleMatrix = Matrix4f().scale(scale)
        val parentMatrix = Matrix4f()

        return parentMatrix.mul(translationMatrix).mul(rotationMatrix).mul(scaleMatrix)
    }

    fun translate(pos: Vector3f) { translate(pos.x, pos.y, pos.z) }
    fun translate(x: Float, y: Float, z: Float) { position.add(x, y, z) }

    fun rotate(axis: Vector3f, angle: Float) {
        rotation.set(Quaternionf().fromAxisAngleDeg(axis, angle).mul(rotation).normalize())
    }

    operator fun plus(other: XH_CTransform): XH_CTransform {
        position.add(other.position)
        rotation.add(other.rotation)
        scale.add(other.scale)

        return this
    }
}