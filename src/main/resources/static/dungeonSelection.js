async function loadDungeons() {
    const response = await fetch('/dungeon');
    const dungeons = await response.json();
    console.log(dungeons);
    const container = document.getElementById("dungeon-container");
    for (let dungeon of dungeons) {
        let params = new URLSearchParams(document.location.search);
        let heroId = params.get("heroId");
        const card = document.createElement("div");
        card.className="dungeon-card";
        card.innerHTML= `
        <h2>${dungeon.name}</h2>
        <input type="checkbox" disabled id="id${dungeon.id}">
        <h3>${dungeon.description}</h3>
        <p>Difficulty: ${dungeon.difficultyLevel}</p>
        <button><a href="/dungeonReport.html?heroId=${heroId}&dungeonId=${dungeon.id}">Visit</a></button>
        `;
        container.appendChild(card);
    }
}
async function loadCompletedDungeon() {
    let params = new URLSearchParams(document.location.search);
    let heroId = params.get("heroId");
    const response = await fetch (`/hero/${heroId}/completedDungeons`);
    const jsonResponse = await response.json();
    for (let dungeonId of jsonResponse) {
       const checkBox = document.getElementById("id" + dungeonId);
       checkBox.checked = true;
    }
}
loadDungeons();
loadCompletedDungeon();