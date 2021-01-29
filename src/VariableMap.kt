class VariableMap(): HashMap<String, Double>() {
    override fun toString(): String {

        var result = "Result: "

        if (this.size == 0){
            result += "Nothing to show"
        } else {
            for (element in this){
                result += " var ${element.key} = ${element.value} \n"
            }
        }

        return result
    }
}