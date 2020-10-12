PSACFILES  := $(shell ls src/main/java/ml/peya/plugins --color=auto)

.DEFAULT_GOAL := help

all:
	make maven

edit: ## Edit makefile
	@edit Makefile

list: ## Show source files in this repo
	@$(foreach val, $(PSACFILES), /bin/ls -dF $(val);)

pom: ## Show pom information
	mvn help:effective-pom

settings: ## Show settings file information
	mvn help:effective-settings

clean: ## Remove built files
	mvn clean

help: ## Self-documented makefile
	@echo
	@echo "Makefile help:"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| sort \
		| awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

deps: ## Show dependency tree
	mvn dependency:tree

maven: ## Compile and package into .jar file
	mvn package

install: ## Execute install commands
	bash install

ant: ## Build in ant
	mvn ant:ant
	ant compile

eclipse: ## Generate a eclipse project
	mvn eclipse:eclipse

idea: ## Generate a IntelliJ IDEA workspace
	mvn idea:idea

javadoc: ## Checkout javadoc branch
	git checkout javadoc

refresh: ## Refresh project files
	mvn ant:ant
	make eclipse
	make idea
	make javadoc
