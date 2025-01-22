function showComponent(component) {
    const content = document.getElementById("content");
    if (component === "user") {
        content.innerHTML = `
            <h2>Update User Information</h2>
            <form id="updateUserForm" action="/updateUser" method="post">
                <label for="firstName">First Name:</label>
                <input
                    type="text"
                    id="firstName"
                    name="firstName"
                    placeholder="${userName}"
                    default="${userName}"
                    required
                />

                <label for="lastName">Last Name:</label>
                <input
                    type="tex   t"
                    id="lastName"
                    name="lastName"
                    default="${userSurname}"
                    placeholder="${userSurname}"
                    required
                />

                <label for="email">Email:</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    default="${userEmail}"
                    placeholder="${userEmail}"
                    required
                />
                <button type="submit">Update</button>
            </form>
        `;
    } else if (component === "options") {
        content.innerHTML = `
            <h2>Options</h2>
            <p>Here you can configure your settings.</p>
        `;
    } else if (component === "terms") {
        content.innerHTML = `
            <h2>Terms and Conditions</h2>
            <h3>Última Atualização: 17/01/2025 </h3>
            <p>
                Bem-vindo ao NuDenk, onde sua jornada financeira é tão divertida quanto educativa! Antes de embarcar nessa aventura, por favor, leia atentamente nossos termos de uso. Eles são como o mapa do tesouro que guiará você em nossa ilha do conhecimento financeiro!
                Ao usar nosso banco digital, você
O Que É o NuDenk?
O NuDenk é uma plataforma interativa que simula as operações de um banco digital, permitindo que você aprenda sobre finanças pessoais, gerenciamento de dinheiro e investimentos de forma lúdica e envolvente. Aqui, você pode criar sua conta, fazer depósitos fictícios, realizar transferências e até mesmo participar de desafios financeiros! interativa que simula as operações de um banco digital, permitindo que você aprenda sobre finanças pessoais, gerenciamento de dinheiro e investimentos de forma lúdica e envolvente. Aqui, você pode criar sua conta, fazer depósitos fictícios, realizar transferências e até mesmo participar de desafios financeiros!
                Criação da Conta
                Para começar sua jornada, você precisará criar uma conta. Lembre-se:
                Idade: Você deve ter pelo menos 13 anos para se inscrever.
                Dados Falsos: Não use informações falsas ou de outra pessoa (a não ser que esteja fazendo um exercício de aprendizado!).
                Segurança:
Como Funciona?
Dinheiro Fictício: Todo o dinheiro que você vê aqui é apenas uma simulação! Não se preocupe; você não pode ficar rico (ou pobre) com isso.
Desafios e Jogos: Participe de desafios financeiros que ajudam a aprender sobre orçamento, economia e investimentos.
Transferências: Você pode enviar e receber dinheiro fictício entre amigos (ou avatares) dentro da plataforma.
Responsabilidades do Usuário
Ao usar o NuDenk, você concorda em:
Ser gentil e respeitoso com outros usuários.
Não usar a plataforma para atividades ilegais ou antiéticas (como piratear ou roubar tesouros!).
Não compartilhar suas credenciais com ninguém (exceto talvez com seu melhor amigo... mas só se ele prometer não contar!).
Propriedade Intelectual
Todos os conteúdos, gráficos e jogos no NuDenk são protegidos por direitos autorais. Você pode usar esses materiais apenas para fins educativos e pessoais. Não copie ou distribua sem nossa permissão (isso é como roubar o mapa do tesouro!).
Modificações nos Termos
Podemos atualizar estes termos de tempos em tempos para refletir mudanças em nossas práticas ou na lei. Quando isso acontecer, avisaremos você através da plataforma (ou talvez com um sinalizador na praia!). É sua responsabilidade revisar esses termos regularmente.
Contato
Se você tiver dúvidas ou sugestões sobre nossos termos ou a plataforma, entre em contato conosco através do nosso suporte ao cliente. Adoramos ouvir nossos aventureiros! Obrigado por escolher o NuDenk! Estamos animados para ajudá-lo a navegar pelas águas das finanças pessoais enquanto se diverte aprendendo. Lembre-se: a educação financeira é uma aventura que vale a pena! Esses Termos de Uso são fictícios e devem ser adaptados conforme necessário para atender às necessidades específicas da sua aplicação e às leis locais aplicáveis.
        </p>`;
    }
}

// Chame a função showComponent com o componente "user"
showComponent("user");
