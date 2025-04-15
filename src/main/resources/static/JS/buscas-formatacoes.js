function buscarCEP() {
    let cep = document.getElementById("cep").value.replace(/\D/g, '');

    if (cep.length !== 8) {
        alert("Digite um CEP válido no formato 00000-000!");
        return;
    }

    fetch(`https://viacep.com.br/ws/${cep}/json/`)
        .then(response => response.json())
        .then(data => {
            if (!data.erro) {
                document.getElementById("endereco").value = data.logradouro;
                document.getElementById("bairro").value = data.bairro;
                document.getElementById("cidade").value = data.localidade;
                document.getElementById("uf").value = data.uf;
            } else {
                alert("CEP não encontrado!");
            }
        })
        .catch(error => console.error("Erro ao buscar CEP:", error));
}
        function formatarCPF(campo) {
            let cpf = campo.value.replace(/\D/g, '');
            if (cpf.length > 11) cpf = cpf.slice(0, 11);
            let cpfFormatado = cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
            campo.value = cpfFormatado;
        }
        function formatarCEP(campo) {
            let cep = campo.value.replace(/\D/g, ''); 
            if (cep.length > 8) cep = cep.slice(0, 8);
            let cepFormatado = cep.replace(/^(\d{5})(\d{3})/, "$1-$2"); 
            campo.value = cepFormatado;
}

        function formatarTelefone(campo) {
            let telefone = campo.value.replace(/\D/g, '');
            if (telefone.length > 11) telefone = telefone.slice(0, 11);
            let telefoneFormatado = telefone.length > 10
                ? `(${telefone.slice(0, 2)}) ${telefone.slice(2, 7)}-${telefone.slice(7)}`
                : `(${telefone.slice(0, 2)}) ${telefone.slice(2, 6)}-${telefone.slice(6)}`;
            campo.value = telefoneFormatado;
        }

        function enableEditing() {
            document.getElementById("name").disabled = false;
            document.getElementById("CPF").disabled = false;
            document.getElementById("telefone").disabled = false;
            document.getElementById("cep").disabled = false;
            document.getElementById("endereco").disabled = false;
            document.getElementById("bairro").disabled = false;
            document.getElementById("cidade").disabled = false;
            document.getElementById("uf").disabled = false;
            document.getElementById("saveButton").disabled = false;
        }

       function deleteUser() {
    let userId = document.getElementById("id").value;
    if (confirm("Tem certeza que deseja excluir este usuário?")) {
        fetch(`/delete/${userId}`, { method: 'DELETE' }) // <-- Corrigido aqui
            .then(response => response.text())
            .then(data => {
                alert(data);
                window.location.href = "/register";
            })
            .catch(error => console.error('Erro ao deletar:', error));
    }
}
function buscarCnpjCpf() {
    const cnpjCpf = document.getElementById("cnpj").value;
    const isPJ = document.getElementById("pessoa_juridica").checked;

    if (isPJ) {
        fetch("/api/cnpj/" + cnpjCpf)
            .then(response => {
                if (!response.ok) {
                    throw new Error("CNPJ não encontrado.");
                }
                return response.json();
            })
            .then(data => {
                document.getElementById("nome").value = data.nome || "";
                document.getElementById("endereco").value = data.logradouro || "";
                document.getElementById("numero_end").value = data.numero || "";
                document.getElementById("bairro").value = data.bairro || "";
                document.getElementById("cidade").value = data.municipio || "";
                document.getElementById("uf").value = data.uf || "";
                document.getElementById("cep").value = data.cep || "";
                document.getElementById("telefone").value = data.telefone || "";
                document.getElementById("email").value = data.email || "";
            })
            .catch(error => {
                alert("Erro ao buscar CNPJ: " + error.message);
                console.error(error);
            });
    } else {
        alert("Busca automática disponível apenas para CNPJ.");
    }
}

function alternarTipoPessoa() {
    const isPJ = document.getElementById("pessoa_juridica").checked;
    const label = document.getElementById("label_cnpj");
    const input = document.getElementById("cnpj");

    if (isPJ) {
        label.innerText = "CNPJ:";
        input.placeholder = "Digite o CNPJ";
    } else {
        label.innerText = "CPF:";
        input.placeholder = "Digite o CPF";
    }
}

window.onload = alternarTipoPessoa;

//buscar beneficiário

function abrirSelecaoBeneficiario() {
  document.getElementById("overlay").style.display = "block";
  filtrarBeneficiarios(); // Buscar todos inicialmente
}

function fecharOverlay() {
  document.getElementById("overlay").style.display = "none";
}

function filtrarBeneficiarios() {
  const cpfCnpj = document.getElementById("filtroCpfCnpj").value;
  const nome = document.getElementById("filtroNome").value;

  fetch(`/api/beneficiarios?cpfCnpj=${cpfCnpj}&nome=${nome}`)
    .then(res => res.json())
    .then(data => {
      const corpo = document.getElementById("corpoTabela");
      corpo.innerHTML = "";

      data.forEach(benef => {
        const linha = document.createElement("tr");

        linha.innerHTML = `
          <td>${benef.id_beneficiario}</td>
          <td>${benef.nomeRazaoSocial}</td>
          <td>${benef.cnpj_cpf}</td>
          <td><button type="button"  onclick="selecionarBeneficiario(${benef.id_beneficiario}, '${benef.nomeRazaoSocial}')">Selecionar</button></td>
        `;

        corpo.appendChild(linha);
      });
    });
}

function selecionarBeneficiario(id, nome) {
  document.getElementById("idBeneficiario").value = id;
  document.getElementById("nomeBeneficiario").value = nome;
  fecharOverlay();
}

function formatarMoeda(campo) {
    let valor = campo.value.replace(/\D/g, '');
    valor = (valor / 100).toFixed(2) + '';
    valor = valor.replace('.', ',');
    valor = valor.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    campo.value = 'R$' + valor;
  }
  function formatarCpfCnpj(campo) {
    let valor = campo.value.replace(/\D/g, '');
  
    if (valor.length <= 11) {
      // Formatar como CPF: 000.000.000-00
      valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
      valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
      valor = valor.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    } else {
      // Formatar como CNPJ: 00.000.000/0000-00
      valor = valor.replace(/^(\d{2})(\d)/, '$1.$2');
      valor = valor.replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3');
      valor = valor.replace(/\.(\d{3})(\d)/, '.$1/$2');
      valor = valor.replace(/(\d{4})(\d{1,2})$/, '$1-$2');
    }
  
    campo.value = valor;
  }