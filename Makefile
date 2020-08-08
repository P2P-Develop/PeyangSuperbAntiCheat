PSACFILES  := $(shell ls src/main/java/ml/peya/plugins --color=auto)

.DEFAULT_GOAL := help

all:
  make maven

edit: ## Edit makefile
  edit Makefile

list: ## Show source files in this repo
	@$(foreach val, $(PSACFILES), /bin/ls -dF $(val);)

pom: ## Show pom information
  mvn help:effective-pom

settings: ## Show settings file information
  mvn help:effective-settings

clean: ## Remove built files
	@mvn clean

help: ## Self-documented Makefile
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| sort \
		| awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

deps: ## Show dependency tree
  mvn dependency:tree

maven: ## Compile and Package to .jar file
  mvn package

install: ## Execute install command
  bash install

ant: ## Build as ant
  mvn ant:ant
  ant compile

eclipse: ## Generate eclipse projects
  mvn eclipse:eclipse

idea: ## Generate IntelliJ IDEA workspace
  mvn idea:idea

javadoc: ## Generate java document files
  mvn javadoc:javadoc

refresh: ## Refresh project files
  make ant
  make eclipse
  make idea
  make javadoc
