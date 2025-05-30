# Etapa de build
FROM openjdk:21-jdk-slim AS build

# Instala dependências necessárias
RUN apt-get update && \
    apt-get install -y curl unzip && \
    rm -rf /var/lib/apt/lists/*

# Cria o diretório da aplicação
WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Dá permissão de execução para o gradlew
RUN chmod +x ./gradlew

# Faz o build usando o gradle wrapper
RUN ./gradlew build --no-daemon

# Etapa final da imagem
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Expõe a porta
EXPOSE 8080

# Copia o JAR gerado da etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Define o comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
