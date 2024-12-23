Feature: Sugestões de Pesquisa

  Scenario: Pesquisa sugestão válida
    Given o usuário acessa a página inicial
    When digita "material escolar" na barra de pesquisa
    Then o sistema deve exibir sugestões de produtos relacionados a "material escolar"

  Scenario: Pesquisa sugestão válida dinâmica
    Given o usuário acessa a página inicial
    When digita "livros" e adiciona "romance" na barra de pesquisa
    Then o sistema deve exibir sugestões de produtos relacionados a "livros de romance"

  Scenario: Pesquisa por departamento
    Given o usuário acessa a página inicial
    When seleciona o departamento "Cozinha"
    When digita "concha"
    Then o sistema deve exibir sugestões de produtos relacionados ao departamento "Cozinha" e produtos relacionados a "concha"

  Scenario: Pesquisa sugestão inválida com caracteres especiais
    Given o usuário acessa a página inicial
    When digita "# $& * * fb444" na barra de pesquisa
    Then o sistema deve exibir uma mensagem de "nenhum resultado encontrado" ou não exibir sugestões.

  Scenario: Pesquisa falha de conexão
    Given o usuário acessa a página inicial
    When altera a conexão com a internet para modo "Slow"
    When tenta digita qualquer texto na barra de pesquisa
    Then o sistema deve exibir mensagem que o sistema está temporariamente indisponível

  Scenario: Pesquisa inválida
    Given o usuário acessa a página inicial
    When digita "asdfghjk"
    Then o sistema deve exibir uma mensagem de "nenhum resultado encontrado" ou não exibir sugestões.
