import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
//	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation(platform("org.hibernate.orm:hibernate-platform:6.4.0.CR1"))
	implementation("org.hibernate.orm:hibernate-core")
	implementation("jakarta.transaction:jakarta.transaction-api")
	implementation("org.postgresql:postgresql")
	// https://mvnrepository.com/artifact/org.hyperledger.fabric/fabric-gateway
	implementation("org.hyperledger.fabric:fabric-gateway:1.4.0")
	// https://mvnrepository.com/artifact/io.grpc/grpc-netty
	implementation("io.grpc:grpc-netty:1.59.1")
	//implementation("io.grpc:grpc-okhttp:1.53.0")
	//implementation("io.spiffe:grpc-netty-macos:0.8.4")
	implementation("org.keycloak:keycloak-admin-client:22.0.1")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")
	runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.76.Final:osx-aarch_64")}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}
