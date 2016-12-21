def errorType = request.getParameter("error")

if (errorType) {
  switch (Integer.valueOf(errorType)) {
    case 1:
      modelMap.put("errorMessage", "E-mail ou senha inválidos, por favor verifique as informações digitadas.")
      break
    case 2:
      modelMap.put("errorMessage", "Você não tem conta ativa no serviço.")
      break
    case 3:
      modelMap.put("errorMessage", "Erro ao reucperar seus cupons.")
      break
    default:
      modelMap.put("errorMessage", "Um erro inesperado ocorreu.")
  }
}
