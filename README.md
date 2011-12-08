# Roy Maven Plugin
Roy maven plugin compiles your [roy scripts](http://roy.brianmckenna.org/ "roy scripts") to javascript. By default it executes during `process-sources` phase.

## Installation
* `git clone https://github.com/folone/roy-maven-plugin`
* `cd roy-maven-plugin`
* `mvn clean install`
Roy plugin is now installed into your local maven repo. You can start using it.

## Usage
Add the following to your pom.xml:

    <project>
    ...
      <properties>
        <js-directory>src/main/webapp/js</js-directory><!-- Path to your js files -->
        <roy-directory>src/main/webapp/roy</roy-directory><!-- Path to your roy files -->
      </properties>
    ...
      <build>
      ...
        <plugins>
          ...
            <plugin>
                <groupId>info.folone.roy.maven</groupId>
                <artifactId>roy-plugin</artifactId>
		<executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                </executions>
              </plugin>
          ...
        </plugins>
      </build>
    </project>
