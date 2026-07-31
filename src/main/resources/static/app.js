const form = document.getElementById("creationForm");
form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = document.getElementById("heroName").value;
    const strengthScore = document.getElementById("strengthScore").value;
    const constitutionScore = document.getElementById("constitutionScore").value;
    const speedScore = document.getElementById("speedScore").value;

    const hero = {
        "name": name,
        "strengthLevel": strengthScore,
        "constitutionLevel": constitutionScore,
        "speedLevel": speedScore,
    }

    const response = await fetch("/hero", {method: "POST", body: JSON.stringify(hero), headers: {"Content-Type": "application/json"}});
    console.log(response.json())
    const container = document.getElementById("hero-container");
    container.innerHTML="";
    await loadHeroes();
    document.getElementById("createDialog").close();
})

async function loadHeroes() {
    const response = await fetch('/hero');
    const heroes = await response.json();
    console.log(heroes);
    const container = document.getElementById("hero-container");
    for (let hero of heroes) {
        const card = document.createElement("div");
        card.onclick = () => {
            window.location = "/dungeonScreen.html?heroId=" + hero.id
        };
        card.className = "hero-card";
        card.innerHTML = `
        <h2>${hero.name}</h2>
        <p>Strength: ${hero.strengthLevel}</p>
        <p>Constitution: ${hero.constitutionLevel}</p>
        <p>Speed: ${hero.speedLevel}</p>
        `;
        container.appendChild(card);
    }

}

loadHeroes();