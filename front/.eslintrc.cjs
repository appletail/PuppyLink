module.exports = {
    "env": {
        "browser": true,
        "es2021": true,
        "node": true
    },
    "settings": {
        "react": {
            "version": "detect",
        },
    },
    "extends": [
        "eslint:recommended",
        "plugin:react/recommended",
        "plugin:@typescript-eslint/recommended",
        "plugin:react/jsx-runtime",
        "plugin:import/recommended",
        "plugin:import/typescript",
        "plugin:react-hooks/recommended",
        "plugin:jsx-a11y/recommended",
        "prettier",
    ],
    "overrides": [
    ],
    "parser": "@typescript-eslint/parser",
    "parserOptions": {
        "ecmaVersion": "latest",
        "sourceType": "module"
    },
    "plugins": [
        "react",
        "@typescript-eslint",
        "import",
        "react-hooks",
        "jsx-a11y",

    ],
    "rules": {
        "react-hooks/rules-of-hooks": "error",
        "react-hooks/exhaustive-deps": "warn",
        "no-console": "warn",
        "react/prop-types": "off",
        "react/button-has-type": "warn",
        "react/self-closing-comp": [
            "warn",
            {
                "component": true,
                "html": false
            }
        ],
        "react/jsx-sort-props": [
            "warn",
            {
                "shorthandFirst": true,
                "callbacksLast": true,
                "noSortAlphabetically": false,
                "reservedFirst": true,
                "multiline": "last",
            },
        ],
        "import/no-unresolved": "off",
        "import/order": [
            "warn",
            {
                "groups": [
                    "builtin",
                    "external",
                    "internal",
                    ["sibling", "parent", "index"],
                    "type",
                    "unknown",
                ],
                "pathGroups": [
                    {
                        "pattern": "{react*,react*/**}",
                        "group": "external",
                        "position": "before",
                    },
                    {
                        "pattern": "{./**/*.module.css,./**/*.css}",
                        "group": "unknown",
                    },
                ],
                "pathGroupsExcludedImportTypes": ["react", "unknown"],
                "newlines-between": "always-and-inside-groups",
                "alphabetize": {
                    "order": "asc",
                    "caseInsensitive": true,
                },
            },
        ],
        "jsx-a11y/anchor-has-content": [
            "warn",
            {
                "components": ["Link"],
            },
        ],
        "jsx-a11y/anchor-is-valid": ["error", {
            "components": ["Link"],
            "specialLink": ["to"]
        }]
    },
}
