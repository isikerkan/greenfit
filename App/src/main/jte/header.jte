@param ch.pa5.greenfit.repository.entity.UserEntity user

<nav>
    <div id="menuToggle">
        <input type="checkbox" id="menuCheckbox">
        <span class="bg-slate-800"></span>
        <span class="bg-slate-800"></span>
        <span class="bg-slate-800"></span>
        <ul id="menu">
            <li><a href="/">
                    <label for="menuState"
                           onclick="this.parentNode.click();">Dashboard</label></a>
            </li>
            <li><a href="/options">
                    <label for="menuState"
                           onclick="this.parentNode.click();">Options</label></a>
            </li>
            @if (user != null)
                <li><a href="/logout">Logout</a></li>
            @endif
        </ul>
    </div>
</nav>
<style>
  * {
    scroll-behavior: smooth;
  }

  /* Menu code starts here */

  #menuToggle {
    display: block;
    /* You can also use relative/absolute here if you want to stay on the top */
    position: fixed;
    top: 50px;
    right: 50px;

    z-index: 1;

    -webkit-user-select: none;
    user-select: none;
  }

  #menuToggle a {
    text-decoration: none;
    color: #232323;

    transition: color 0.3s ease;
  }

  #menuToggle a:hover {
    color: tomato;
  }


  #menuToggle input {
    display: block;
    width: 40px;
    height: 32px;
    position: absolute;
    top: -7px;
    right: -5px;

    cursor: pointer;

    opacity: 0; /* hide this */
    z-index: 2; /* and place it over the hamburger */

    -webkit-touch-callout: none;
  }

  /*
   * Just a quick hamburger
   */
  #menuToggle span {
    display: block;
    width: 33px;
    height: 4px;
    margin-bottom: 5px;
    position: relative;

    border-radius: 3px;

    z-index: 1;

    transform-origin: 4px 0px;

    transition: transform 0.5s cubic-bezier(0.77, 0.2, 0.05, 1.0),
    background 0.5s cubic-bezier(0.77, 0.2, 0.05, 1.0),
    opacity 0.55s ease;
  }

  #menuToggle span:first-child {
    transform-origin: 0% 0%;
  }

  #menuToggle span:nth-last-child(2) {
    transform-origin: 0% 100%;
  }

  /*
   * Transform all the slices of hamburger
   * into a crossmark.
   */
  #menuToggle input:checked ~ span {
    opacity: 1;
    transform: rotate(45deg) translate(-2px, -1px);
    background: #232323;
  }

  /*
   * But let's hide the middle one.
   */
  #menuToggle input:checked ~ span:nth-last-child(3) {
    opacity: 0;
    transform: rotate(0deg) scale(0.2, 0.2);
  }

  /*
   * Ohyeah and the last one should go the other direction
   */
  #menuToggle input:checked ~ span:nth-last-child(2) {
    transform: rotate(-45deg) translate(0, -1px);
  }

  /*
   * Make this absolute positioned
   * at the top right of the screen
   */
  #menu {
    position: absolute;
    max-width: 400px;
    width: 100vw;
    max-height: 100vh;
    margin: -100px 0 0 -50px;
    padding: 50px;
    padding-top: 125px;
    box-sizing: border-box;
    overflow-y: auto;
    background: #ededed;
    list-style-type: none;
    -webkit-font-smoothing: antialiased;
    /* to stop flickering of text in safari */

    transform-origin: 0% 0%;
    transform: translate(100%, 0);

    transition: transform 0.5s cubic-bezier(0.77, 0.2, 0.05, 1.0);
  }

  #menu li {
    padding: 10px 0;
    font-size: 22px;
  }

  #menu li label {
    cursor: pointer;
  }

  /*
   * And let's slide it in from the right
   */
  #menuToggle input:checked ~ ul {
    transform: translate(-70%, 0)
  }</style>
