export default function() {
	if (localStorage.getItem("basePath") === null) {
		return "";
	}
	return localStorage.getItem("basePath");
}