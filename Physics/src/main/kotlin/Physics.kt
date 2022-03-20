import templates.IEngine

object Physics : IEngine {

    var gravityAmplifier: Float = 0.0f
        private set
    var defaultUseAirFriction: Boolean = false
        private set
    var defaultUseMaterialFriction: Boolean = false
        private set

    var defaultZLayerFor2D: Int = 0
        private set
    var useCollisionGrid: Boolean = false
        private set
    var collisionGridScale: Float = 0.0f
        private set

    var collisionGrid: Array<Array<Array<Int>>>? = null

    override fun onAwake() {
        gravityAmplifier = config("physics.gravityAmplifier") as Float
        defaultUseAirFriction = config("physics.useAirFriction") as Boolean
        defaultUseMaterialFriction = config("physics.useMaterialFriction") as Boolean
        defaultZLayerFor2D = config("physics.defaultZLayerFor2D") as Int
        useCollisionGrid = config("physics.useCollisionGrid") as Boolean
        collisionGridScale = config("physics.collisionGridScale") as Float
    }

}