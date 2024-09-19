package demo.archunit

class ProductionCodeRulesTest : ArchitectureRules, NamingRules {
    override val basePackage: String = "com.acme"
}
