define HELP_MESSAGE
Builds and tests java-employee-challenge
endef

BUILD_TOOL := ./mvnw
JAR_FILE := $(shell find target -name '*.jar' 2>/dev/null)
MEM_OPTS := -Xms128m -Xmx128m -XX:MaxMetaspaceSize=128m
JMX_OPTS := -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=39666 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false

help :
	$(info ${HELP_MESSAGE})
	@echo "######################################### Targets ##########################################################"
	@echo "build = build project"
	@echo "clean = clean project"
	@echo "clean-build = clean and then build project"
	@echo "test = run unit tests"
	@echo "run = run application"
	@echo "############################################################################################################"


build :		## Build the application excluding tests
	$(BUILD_TOOL) package -DskipTests

clean :		## Clean the application
	$(BUILD_TOOL) clean

clean-build :		## Clean and Build the application excluding tests
	$(BUILD_TOOL) clean package -DskipTests

test : clean-build		## Run all tests in the application
	$(BUILD_TOOL) test

run :		## Run the application through the generated fat-jar
	java $(MEM_OPTS) $(JMX_OPTS) -jar $(JAR_FILE)
