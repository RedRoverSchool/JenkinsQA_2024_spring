# Используем базовый образ ubuntu:java-tools-latest от act
FROM catthehacker/ubuntu:java-tools-latest

# Устанавливаем рабочий каталог внутри контейнера
WORKDIR /tests

# Обновляем пакеты и устанавливаем недостающее для успешного запуска Xрома
RUN export DEBIAN_FRONTEND=noninteractive \
    && apt-get update \
    && apt-get install -y \
    libgbm1 \
    libgtk-3-0 \
    libnss3 \
    && rm -rf /var/lib/apt/lists/*

# Скачиваем и устанавливаем последние стабильные версии Google Chrome и ChromeDriver
RUN curl -L 'https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions-with-downloads.json' \
    | jq -r '.channels.Stable.downloads|.chrome,.chromedriver|.[]|select(.platform=="linux64").url|"curl -LO \(.)"' \
    | bash \
    && unzip -d /usr/local/share 'chrome-linux64.zip' \
    && ln -s /usr/local/share/chrome-linux64/chrome /usr/bin/chrome \
    && unzip -d /usr/local/share 'chromedriver-linux64.zip' \
    && ln -s /usr/local/share/chromedriver-linux64/chromedriver /usr/bin/chromedriver \
    && rm 'chrome-linux64.zip' && rm 'chromedriver-linux64.zip'

## JAVA_HOME
#RUN export JAVA_HOME=/usr/lib/jvm/temurin-17-jdk-amd64

## Скачиваем и устанавливаем allure
#RUN curl -LO 'https://github.com/allure-framework/allure2/releases/download/2.24.1/allure_2.24.1-1_all.deb' \
#    && dpkg -i 'allure_2.24.1-1_all.deb' \
#    && rm 'allure_2.24.1-1_all.deb'

# Копируем pom.xml и src из текущего контекста сборки в рабочий каталог
ADD pom.xml /tests
COPY ./src /tests/src

ENV JAVA_HOME /usr/lib/jvm/temurin-17-jdk-amd64

# Запускаем maven
CMD ["mvn","test"]
#CMD ["mvn", "-Dtest=*MultiConfiguration*", "test"]
#CMD ["mvn", "-Dtest=*Folder4*", "test"]
#CMD ["mvn", "-Dtest=*100*,MultiConfigurationProjectTest,NewItem11Test", "test"]
#CMD ["sleep", "infinity"]