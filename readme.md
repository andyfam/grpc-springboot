# springboot
A demo project show how to use springboot. Included features: REST, Grpc.

## Requirements
- Java 17+
- Gradle 8.7+

## Setup
We use [start.spring.io](https://start.spring.io/) to generate the springboot framework, choose the following Dependencies: JPA, H2, Web, Lombok

### Gradle Dependencies
in order to support Grpc, we need to add the following dependencies:
```
dependencies {
    implementation 'net.devh:grpc-spring-boot-starter:3.1.0.RELEASE'
    implementation 'net.devh:grpc-client-spring-boot-starter:3.1.0.RELEASE'
    testImplementation group: 'io.grpc', name: 'grpc-testing', version: '1.64.0'
    
    if (JavaVersion.current().isJava9Compatible()) {		
        implementation 'javax.annotation:javax.annotation-api:1.3.1'
    }
}
```

in order to use the validation feature, add the following dependency:
```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-validation:3.3.0'
}
```
### Gradle plugin
in order to support Grpc, we need to add the following plugin and configration:
```
plugins {
	id 'com.google.protobuf' version '0.9.4'
}
```
```
protobuf {
	protoc {
		artifact = 'com.google.protobuf:protoc:3.6.1'
	}
	plugins {
		grpc {
			artifact = 'io.grpc:protoc-gen-grpc-java:1.15.1'
		}
	}
	generateProtoTasks {
		ofSourceSet('main').configureEach {
			plugins {
				grpc { }
			}
		}
	}
}
```

## usage
### REST
### Grpc
