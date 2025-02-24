function openRecommendations(category) {
  console.log(category)
  console.log("open recommendations")
  const hiddenPanels = document.querySelectorAll(".hidden-games");
  hiddenPanels.forEach(hide) ;
  const selectedRecommendations = document.getElementsByName(category);
  selectedRecommendations.forEach(display)
}

const hide = elem => {
  elem.style.display = 'none'
}

const display = elem => {
  elem.style.display = 'inline'
}