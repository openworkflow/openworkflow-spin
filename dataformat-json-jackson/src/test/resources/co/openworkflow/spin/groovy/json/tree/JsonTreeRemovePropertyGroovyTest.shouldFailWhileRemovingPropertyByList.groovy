package co.openworkflow.spin.groovy.json.tree

node = S(input, "application/json")
def list = ["order", "comment"]
node.deleteProp(list)