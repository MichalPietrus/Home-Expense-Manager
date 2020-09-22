function printNavbar() {
    let navbar = document.getElementById("navbar");
    navbar.innerHTML = `
    <a class="navbar-brand" href="/">Expense manager</a>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active dropdown" >
                <a class="nav-link dropdown-toggle" href="#" id="navbarCategories" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Categories
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarCategories">
                    <a class="dropdown-item" href="/category/list/0">Categories list</a>
                    <a class="dropdown-item" href="/category/add">Add Category</a>
                </div>
            </li>
            <li class="nav-item active dropdown" >
                <a class="nav-link dropdown-toggle" href="#" id="transaction" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Transactions
                </a>
                <div class="dropdown-menu" aria-labelledby="transaction">
                    <a class="dropdown-item" href="/transaction/add/income">Add income</a>
                    <a class="dropdown-item" href="/transaction/add/outcome">Add outcome</a>
                    <a class="dropdown-item" href="/transaction/history/0">Transaction History</a>
                </div>
            </li>
            <li class="nav-item active" >
                <a class="nav-link" href="/logout">Logout</a>
            </li>
        </ul>
    </div>
    `;
}